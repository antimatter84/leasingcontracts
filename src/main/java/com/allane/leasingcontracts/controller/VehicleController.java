package com.allane.leasingcontracts.controller;

import com.allane.leasingcontracts.dto.VehicleDTO;
import com.allane.leasingcontracts.service.VehicleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = UrlConstants.VEHICLES_V1_URL)
public class VehicleController
{
    private final VehicleService vehicleService;

    @Autowired
    public VehicleController(VehicleService vehicleService)
    {
        this.vehicleService = vehicleService;
    }

    @GetMapping
    public ResponseEntity<List<VehicleDTO>> getVehicles()
    {
        return new ResponseEntity<>(vehicleService.getVehicles(), HttpStatus.OK);
    }

    @GetMapping(path = "/brands")
    public ResponseEntity<List<String>> getVehicleBrands()
    {
        return new ResponseEntity<>(vehicleService.getVehicleBrands(), HttpStatus.OK);
    }

    @PostMapping
    public void addVehicle(@RequestBody VehicleDTO vehicleDto)
    {
        vehicleService.saveVehicle(vehicleDto);
    }

    @DeleteMapping(path = "{vehicleId}")
    public void deleteVehicle(@PathVariable("vehicleId") Long vehicleId)
    {
        vehicleService.deleteVehicle(vehicleId);
    }

    @PutMapping(path = "{vehicleId}")
    public void updateVehicle(
        @PathVariable("vehicleId") Long vehicleId,
        @RequestBody VehicleDTO vehicleDto
    )
    {
        vehicleService.updateVehicle(vehicleId, vehicleDto);
    }
}
