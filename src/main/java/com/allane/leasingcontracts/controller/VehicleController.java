package com.allane.leasingcontracts.controller;

import com.allane.leasingcontracts.model.Vehicle;
import com.allane.leasingcontracts.service.VehicleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping
    public void addVehicle(@RequestBody Vehicle vehicle)
    {
        vehicleService.saveVehicle(vehicle);
    }

    @DeleteMapping(path = "{vehicleId}")
    public void deleteVehicle(@PathVariable("vehicleId") Long vehicleId)
    {
        vehicleService.deleteVehicle(vehicleId);
    }

    @PutMapping(path = "{vehicleId}")
    public void updateVehicle(
            @PathVariable("vehicleId") Long vehicleId,
            @RequestParam(required = false) int year,
            @RequestParam(required = false) float price
    )
    {
        vehicleService.updateVehicle(vehicleId, year, price);
    }
}
