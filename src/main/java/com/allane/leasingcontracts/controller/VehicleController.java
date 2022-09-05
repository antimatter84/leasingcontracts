package com.allane.leasingcontracts.controller;

import com.allane.leasingcontracts.model.Vehicle;
import com.allane.leasingcontracts.service.VehicleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(path = "api/v1/vehicles")
public class VehicleController
{
    private final VehicleService vehicleService;

    @Autowired
    public VehicleController(VehicleService vehicleService)
    {
        this.vehicleService = vehicleService;
    }

    @GetMapping
    public List<Vehicle> getVehicles()
    {
        return vehicleService.getVehicles();
    }


}
