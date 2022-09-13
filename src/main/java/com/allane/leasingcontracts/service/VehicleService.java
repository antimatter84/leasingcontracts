package com.allane.leasingcontracts.service;

import com.allane.leasingcontracts.model.Vehicle;
import com.allane.leasingcontracts.repository.VehicleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class VehicleService
{
    private final VehicleRepository vehicleRepository;

    @Autowired
    public VehicleService(VehicleRepository vehicleRepository)
    {
        this.vehicleRepository = vehicleRepository;
    }

    public List<Vehicle> getVehicles()
    {
        return vehicleRepository.findAll();
    }

    public void addVehicle(Vehicle vehicle)
    {
        Optional<Vehicle> vehicleOptional = vehicleRepository.findVehicleByVin(vehicle.getVin());
        if (vehicleOptional.isPresent())
        {
            throw new IllegalStateException("VIN already registered.");
        }
        vehicleRepository.save(vehicle);
    }

    public void deleteVehicle(Long vehicleId)
    {
        boolean exists = vehicleRepository.existsById(vehicleId);
        if (!exists)
        {
            throw new IllegalStateException("Vehicle with ID " + vehicleId + " does not exist.");
        }

        vehicleRepository.deleteById(vehicleId);
    }

    @Transactional
    public void updateVehicle(Long vehicleId, int year, float price)
    {
        Vehicle vehicle = vehicleRepository.findById(vehicleId)
                .orElseThrow(() -> new IllegalStateException("Vehicle ID not found: " + vehicleId));

        if (year > 0)
        {
            vehicle.setModelYear(year);
        }

        if (price > 0)
        {
            vehicle.setPrice(price);
        }
    }
}
