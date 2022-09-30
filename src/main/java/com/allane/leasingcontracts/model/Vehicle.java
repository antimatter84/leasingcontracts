package com.allane.leasingcontracts.model;

import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
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

    private BigDecimal price;

    @OneToOne(
        mappedBy = "vehicle"
    )
    @ToString.Exclude
    private LeasingContract contract;

    public Vehicle(String brand, String model, int modelYear, String vin, BigDecimal price)
    {
        this.brand = brand;
        this.model = model;
        this.modelYear = modelYear;
        this.vin = vin;
        this.price = price;
    }

    public String getDescription()
    {
        return String.format("%s %s (%d)", brand, model, modelYear);
    }
}
