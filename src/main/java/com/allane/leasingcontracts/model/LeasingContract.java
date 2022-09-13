package com.allane.leasingcontracts.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
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
    private float leasingRate;

    @ManyToOne(
            fetch = FetchType.LAZY
    )
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
