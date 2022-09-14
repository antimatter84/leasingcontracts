package com.allane.leasingcontracts.service;

import com.allane.leasingcontracts.model.LeasingContract;
import com.allane.leasingcontracts.repository.LeasingContractRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class LeasingContractService
{
    private final LeasingContractRepository contractRepository;

    @Autowired
    public LeasingContractService(LeasingContractRepository customerRepository)
    {
        this.contractRepository = customerRepository;
    }

    public List<LeasingContract> getContracts()
    {
        return contractRepository.findAll();
    }

    public void addContract(LeasingContract contract)
    {
        Optional<LeasingContract> contractOptional = contractRepository.findByContractNumber(contract.getContractNumber());
        if (contractOptional.isPresent())
        {
            throw new IllegalStateException(
                    "Contract with same contract number already exists: " + contract.getContractNumber());
        }

        boolean vehicleAlreadyLeased = contractRepository.existsLeasingContractByVehicle(contract.getVehicle());
        if (vehicleAlreadyLeased)
        {
            throw new IllegalStateException(
                    "Cannot create leasing contract. Vehicle already in use: " + contract.getVehicle());
        }

        contractRepository.save(contract);
    }

    public void deleteContract(Long contractId)
    {
        boolean exists = contractRepository.existsById(contractId);
        if (!exists)
        {
            throw new IllegalStateException("Leasing contract with ID " + contractId + " does not exist");
        }

        contractRepository.deleteById(contractId);
    }

    @Transactional
    public void updateContract(Long contractId, LeasingContract contract)
    {
        LeasingContract leasingContract = contractRepository.findById(contractId)
                .orElseThrow(() -> new IllegalStateException("Leasing contract with ID " + contractId + " does not exist"));

        boolean contractNumberUsed = contractRepository.existsLeasingContractByContractNumber(contract.getContractNumber());
        if (contractNumberUsed)
        {
            throw new IllegalStateException("Leasing contract number " + contract.getContractNumber() + " already exists.");
        }

        boolean vehicleInUse = contractRepository.existsLeasingContractByVehicle(contract.getVehicle());
        if (vehicleInUse)
        {
            throw new IllegalStateException(
                    "Cannot update leasing contract. Vehicle already in use: " + contract.getVehicle());
        }

        // update contract
        leasingContract.setContractNumber(contract.getContractNumber());
        leasingContract.setLeasingRate(contract.getLeasingRate());
        leasingContract.setCustomer(contract.getCustomer());
        leasingContract.setVehicle(contract.getVehicle());
    }
}
