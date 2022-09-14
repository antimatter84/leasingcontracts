package com.allane.leasingcontracts.repository;

import com.allane.leasingcontracts.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface CustomerRepository
        extends JpaRepository<Customer, Long>
{
    List<Customer> findByFirstNameAndLastNameAndDateOfBirth(String firstName, String lastName, LocalDate dateOfBirth);
}
