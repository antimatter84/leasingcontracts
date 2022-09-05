package com.allane.leasingcontracts.repository;

import com.allane.leasingcontracts.model.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VehicleRepository
        extends JpaRepository<Vehicle, Long>
{
}
