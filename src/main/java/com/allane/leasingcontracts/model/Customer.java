package com.allane.leasingcontracts.model;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@Table(name = "customer")
public class Customer
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "customer_id")
    private Long id;

    @Column(name = "firstname")
    private String firstName;

    @Column(name = "lastname")
    private String lastName;

    @Column(name = "date_of_birth")
    private LocalDate dateOfBirth;

    @OneToMany(
        mappedBy = "customer",
        cascade = CascadeType.ALL,
        orphanRemoval = true
    )
    @ToString.Exclude
    private List<LeasingContract> contracts = new ArrayList<>();

    public Customer(String firstName, String lastName, LocalDate dateOfBirth)
    {
        this.firstName = firstName;
        this.lastName = lastName;
        this.dateOfBirth = dateOfBirth;
    }

    public void addContract(LeasingContract contract)
    {
        this.contracts.add(contract);
        contract.setCustomer(this);
    }

    public void removeContract(LeasingContract contract)
    {
        this.contracts.remove(contract);
        contract.setCustomer(null);
    }

    public String getFullName()
    {
        return String.format("%s %s", firstName, lastName);
    }
}
