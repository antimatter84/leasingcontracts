package com.allane.leasingcontracts.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NewContractDTO
{
    private Long id;
    private String contractNumber;
    private BigDecimal leasingRate;
    private Long customerId;
    private Long vehicleId;

    public NewContractDTO(String contractNumber, BigDecimal leasingRate, Long customerId, Long vehicleId)
    {
        this.contractNumber = contractNumber;
        this.leasingRate = leasingRate;
        this.customerId = customerId;
        this.vehicleId = vehicleId;
    }
}
