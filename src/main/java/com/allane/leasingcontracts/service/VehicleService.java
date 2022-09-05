package com.allane.leasingcontracts.service;

import com.allane.leasingcontracts.model.Vehicle;
import com.allane.leasingcontracts.repository.VehicleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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


}
