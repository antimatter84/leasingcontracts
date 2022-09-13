package com.allane.leasingcontracts.repository;

import com.allane.leasingcontracts.model.LeasingContract;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LeasingContractRepository
        extends JpaRepository<LeasingContract, Long>
{
}
