package com.allane.leasingcontracts.repository;

import com.allane.leasingcontracts.model.LeasingContract;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LeasingContractRepository
        extends JpaRepository<LeasingContract, Long>
{
    Optional<LeasingContract> findByContractNumber(String contractNumber);

    boolean existsLeasingContractByVehicleId(Long vehicleId);

    boolean existsLeasingContractByContractNumber(String contractNumber);
}
