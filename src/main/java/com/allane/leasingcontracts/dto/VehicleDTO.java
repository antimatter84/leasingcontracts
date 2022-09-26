package com.allane.leasingcontracts.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Data
@NoArgsConstructor
@RequiredArgsConstructor
public class VehicleDTO
{
    private Long id;
    @NonNull
    private String brand;
    @NonNull
    private String model;
    @NonNull
    private int modelYear;
    @NonNull
    private String vin;
    @NonNull
    private float price;
}
