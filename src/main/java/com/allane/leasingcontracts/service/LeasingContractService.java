package com.allane.leasingcontracts.service;

import com.allane.leasingcontracts.dto.LeasingContractDTO;
import com.allane.leasingcontracts.dto.NewContractDTO;
import com.allane.leasingcontracts.model.Customer;
import com.allane.leasingcontracts.model.LeasingContract;
import com.allane.leasingcontracts.model.Vehicle;
import com.allane.leasingcontracts.repository.CustomerRepository;
import com.allane.leasingcontracts.repository.LeasingContractRepository;
import com.allane.leasingcontracts.repository.VehicleRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class LeasingContractService
{
    private final LeasingContractRepository contractRepository;
    private final ModelMapper mapper;

    private final CustomerRepository customerRepository;
    private final VehicleRepository vehicleRepository;

    @Autowired
    public LeasingContractService(LeasingContractRepository contractRepository,
                                  ModelMapper mapper,
                                  CustomerRepository customerRepository,
                                  VehicleRepository vehicleRepository)
    {
        this.contractRepository = contractRepository;
        this.mapper = mapper;
        this.customerRepository = customerRepository;
        this.vehicleRepository = vehicleRepository;
    }

    public List<LeasingContractDTO> getContracts()
    {
        return contractRepository.findAll()
            .stream()
            .map(c -> mapper.map(c, LeasingContractDTO.class))
            .collect(Collectors.toList());
    }

    public LeasingContractDTO getContract(Long id)
    {
        Optional<LeasingContract> contract = contractRepository.findById(id);
        if (contract.isEmpty())
        {
            throw new IllegalArgumentException("No contract found with ID " + id);
        }

        return mapper.map(contract, LeasingContractDTO.class);
    }

    public LeasingContractDTO addContract(NewContractDTO contractDto)
    {
        if (contractNumberExists(contractDto.getContractNumber()))
        {
            throw new IllegalStateException(
                "Contract with same contract number already exists: " + contractDto.getContractNumber());
        }

        if (vehicleAlreadyLeased(contractDto.getVehicleId()))
        {
            throw new IllegalStateException(
                "Cannot create leasing contract. Vehicle already in use: " + contractDto.getVehicleId());
        }

        Customer customer = customerRepository.findById(contractDto.getCustomerId())
            .orElseThrow(() -> new IllegalArgumentException("Cannot find customer with ID " + contractDto.getCustomerId()));

        Vehicle vehicle = vehicleRepository.findById(contractDto.getVehicleId())
            .orElseThrow(() -> new IllegalArgumentException("Cannot find vehicle with ID " + contractDto.getVehicleId()));

        LeasingContract contract = LeasingContract.builder()
            .contractNumber(contractDto.getContractNumber())
            .leasingRate(contractDto.getLeasingRate())
            .customer(customer)
            .vehicle(vehicle)
            .build();

        LeasingContract savedContract = contractRepository.save(contract);
        return mapper.map(savedContract, LeasingContractDTO.class);

    }

    public LeasingContractDTO deleteContract(Long contractId)
    {
        Optional<LeasingContract> contract = contractRepository.findById(contractId);
        if (contract.isEmpty())
        {
            throw new IllegalArgumentException("Leasing contract with ID " + contractId + " does not exist");
        }

        contractRepository.deleteById(contractId);
        return mapper.map(contract.get(), LeasingContractDTO.class);
    }

    @Transactional
    public void updateContract(Long contractId, NewContractDTO contractDto)
    {
        LeasingContract leasingContract = contractRepository.findById(contractId)
            .orElseThrow(() -> new IllegalStateException("Leasing contract with ID " + contractId + " does not exist"));

        if (contractNumberExists(contractDto.getContractNumber()))
        {
            throw new IllegalStateException("Leasing contract number " + contractDto.getContractNumber() + " already exists.");
        }

        if (vehicleAlreadyLeased(contractDto.getVehicleId()))
        {
            throw new IllegalStateException(
                "Cannot update leasing contract. Vehicle already in use: " + contractDto.getVehicleId());
        }

        Customer customer = customerRepository.findById(contractDto.getCustomerId())
            .orElseThrow(() -> new IllegalArgumentException("Cannot find customer with ID " + contractDto.getCustomerId()));

        Vehicle vehicle = vehicleRepository.findById(contractDto.getVehicleId())
            .orElseThrow(() -> new IllegalArgumentException("Cannot find vehicle with ID " + contractDto.getVehicleId()));

        // update contract
        leasingContract.setContractNumber(contractDto.getContractNumber());
        leasingContract.setLeasingRate(contractDto.getLeasingRate());
        leasingContract.setCustomer(customer);
        leasingContract.setVehicle(vehicle);
    }

    private boolean contractNumberExists(String contractNumber)
    {
        return contractRepository.existsLeasingContractByContractNumber(contractNumber);
    }

    private boolean vehicleAlreadyLeased(Long vehicleId)
    {
        return contractRepository.existsLeasingContractByVehicleId(vehicleId);
    }
}
