package com.allane.leasingcontracts.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Customer
{
    private long id;
    private String firstName;
    private String lastName;
    private LocalDate dateOfBirth;

    public Customer(String firstName, String lastName, LocalDate dateOfBirth)
    {
        this.firstName = firstName;
        this.lastName = lastName;
        this.dateOfBirth = dateOfBirth;
    }
}
