package com.allane.leasingcontracts.repository;

import com.allane.leasingcontracts.model.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface VehicleRepository
    extends JpaRepository<Vehicle, Long>
{
    Optional<Vehicle> findVehicleByVin(String vin);

    boolean existsByIdIsNotAndVinEquals(Long id, String vin);

    @Query("select distinct brand from Vehicle")
    List<String> findDistinctBrands();
}
