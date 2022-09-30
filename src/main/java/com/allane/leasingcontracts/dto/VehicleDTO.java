package com.allane.leasingcontracts.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
public class VehicleDTO
{
    private Long id;
    private String brand;
    private String model;
    private int modelYear;
    private String vin;
    private BigDecimal price;

    public VehicleDTO(String brand, String model, int modelYear, String vin, BigDecimal price)
    {
        this.brand = brand;
        this.model = model;
        this.modelYear = modelYear;
        this.vin = vin;
        this.price = price;
    }
}
