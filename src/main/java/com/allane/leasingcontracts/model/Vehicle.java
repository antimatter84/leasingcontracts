package com.allane.leasingcontracts.model;

import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
public class Vehicle
{
    @Id
    @SequenceGenerator(
            name = "vehicle_sequence",
            sequenceName = "vehicle_sequence",
            allocationSize = 1)
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "vehicle_sequence"
    )
    private Long id;

    private String brand;

    private String model;

    private int modelYear;

    private String vin;

    private float price;

    public Vehicle(String brand, String model, int modelYear, String vin, float price)
    {
        this.brand = brand;
        this.model = model;
        this.modelYear = modelYear;
        this.vin = vin;
        this.price = price;
    }

    public Vehicle(String brand, String model, int modelYear, float price)
    {
        this.brand = brand;
        this.model = model;
        this.modelYear = modelYear;
        this.price = price;
    }
}
