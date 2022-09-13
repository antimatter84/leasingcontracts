package com.allane.leasingcontracts.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@ToString
@Entity
@Table(name = "vehicle")
public class Vehicle
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "vehicle_id")
    private Long id;

    private String brand;

    private String model;

    @Column(name = "model_year")
    private int modelYear;

    private String vin;

    private float price;

    @OneToOne(
            fetch = FetchType.LAZY,
            mappedBy = "vehicle"
    )
    private LeasingContract contract;

    public Vehicle(String brand, String model, int modelYear, String vin, float price)
    {
        this.brand = brand;
        this.model = model;
        this.modelYear = modelYear;
        this.vin = vin;
        this.price = price;
    }
}
