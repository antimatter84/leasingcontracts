package com.allane.leasingcontracts.model;

import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "contract")
public class LeasingContract
{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "contract_id")
    private Long id;

    @Column(name = "contract_number")
    private String contractNumber;

    @Column(name = "leasing_rate")
    private BigDecimal leasingRate;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @OneToOne
    @JoinColumn(name = "vehicle_id")
    private Vehicle vehicle;

    public void setVehicle(Vehicle vehicle)
    {
        if (vehicle == null)
        {
            if (this.vehicle != null)
            {
                this.vehicle.setContract(null);
            }
        }
        else
        {
            vehicle.setContract(this);
        }
        this.vehicle = vehicle;
    }

    @Override
    public boolean equals(Object obj)
    {
        if (this == obj)
        {
            return true;
        }
        if (!(obj instanceof LeasingContract))
        {
            return false;
        }

        LeasingContract otherContract = (LeasingContract) obj;
        return this.id != null
                && this.id.equals(otherContract.getId());
    }

    @Override
    public int hashCode()
    {
        return getClass().hashCode();
    }


}
