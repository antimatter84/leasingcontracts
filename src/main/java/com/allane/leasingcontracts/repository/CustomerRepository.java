package com.allane.leasingcontracts.repository;

import com.allane.leasingcontracts.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository
        extends JpaRepository<Customer, Long>
{
}
