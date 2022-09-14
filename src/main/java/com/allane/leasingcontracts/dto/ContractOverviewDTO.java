package com.allane.leasingcontracts.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ContractOverviewDTO
{
    private long id;
    private String contractNumber;
    private String fullName;
    private String vehicleDescription;
    private String vin;
    private float rate;
    private float price;
}
