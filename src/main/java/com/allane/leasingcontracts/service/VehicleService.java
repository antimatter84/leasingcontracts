package com.allane.leasingcontracts.service;

import com.allane.leasingcontracts.dto.VehicleDTO;
import com.allane.leasingcontracts.model.Vehicle;
import com.allane.leasingcontracts.repository.VehicleRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class VehicleService
{
    private final VehicleRepository vehicleRepository;
    private final ModelMapper mapper;

    @Autowired
    public VehicleService(VehicleRepository vehicleRepository, ModelMapper mapper)
    {
        this.vehicleRepository = vehicleRepository;
        this.mapper = mapper;
    }

    public List<VehicleDTO> getVehicles()
    {
        return vehicleRepository.findAll().stream()
            .map(v -> mapper.map(v, VehicleDTO.class))
            .collect(Collectors.toList());
    }

    public Vehicle saveVehicle(VehicleDTO vehicleDto)
    {
        Vehicle vehicle = mapper.map(vehicleDto, Vehicle.class);
        if (vehicle.getVin() != null && vehicleRepository.findVehicleByVin(vehicle.getVin()).isPresent())
        {
            throw new IllegalArgumentException("VIN already registered.");
        }
        return vehicleRepository.save(vehicle);
    }

    public void deleteVehicle(Long vehicleId)
    {
        boolean exists = vehicleRepository.existsById(vehicleId);
        if (!exists)
        {
            throw new IllegalArgumentException("Vehicle with ID " + vehicleId + " does not exist.");
        }

        vehicleRepository.deleteById(vehicleId);
    }

    @Transactional
    public VehicleDTO updateVehicle(Long vehicleId, VehicleDTO vehicleDto)
    {
        Vehicle vehicle = vehicleRepository.findById(vehicleId)
            .orElseThrow(() -> new IllegalStateException("Vehicle ID not found: " + vehicleId));

        // check VIN
        if (vehicleRepository.existsByIdIsNotAndVinEquals(vehicleId, vehicleDto.getVin()))
        {
            throw new IllegalArgumentException("VIN already assigned to another vehicle.");
        }

        vehicle.setBrand(vehicleDto.getBrand());
        vehicle.setModel(vehicleDto.getModel());
        vehicle.setModelYear(vehicleDto.getModelYear());
        vehicle.setVin(vehicleDto.getVin());
        vehicle.setPrice(vehicleDto.getPrice());

        return mapper.map(vehicle, VehicleDTO.class);
    }
}
