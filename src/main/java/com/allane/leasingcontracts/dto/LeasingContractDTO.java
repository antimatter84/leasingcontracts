package com.allane.leasingcontracts.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LeasingContractDTO
{
    private long id;
    private String contractNumber;
    private String fullName;
    private String vehicleDescription;
    private String vin;
    private BigDecimal rate;
    private BigDecimal price;
}
