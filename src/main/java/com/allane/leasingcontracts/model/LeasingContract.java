package com.allane.leasingcontracts.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LeasingContract
{
    private long id;
    private long contractNumber;
    private float leasingRate;

    public LeasingContract(long contractNumber, float leasingRate)
    {
        this.contractNumber = contractNumber;
        this.leasingRate = leasingRate;
    }
}
