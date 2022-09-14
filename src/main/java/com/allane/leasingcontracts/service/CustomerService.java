package com.allane.leasingcontracts.service;

import com.allane.leasingcontracts.model.Customer;
import com.allane.leasingcontracts.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class CustomerService
{
    private final CustomerRepository customerRepository;

    @Autowired
    public CustomerService(CustomerRepository customerRepository)
    {
        this.customerRepository = customerRepository;
    }

    public List<Customer> getCustomers()
    {
        return customerRepository.findAll();
    }

    public void addCustomer(Customer customer)
    {
        List<Customer> existing = customerRepository.findByFirstNameAndLastNameAndDateOfBirth(
                customer.getFirstName(), customer.getLastName(), customer.getDateOfBirth());
        if (!existing.isEmpty())
        {
            throw new IllegalStateException("Customer already exists.");
        }

        customerRepository.save(customer);
    }

    public void deleteCustomer(Long customerId)
    {
        boolean exists = customerRepository.existsById(customerId);
        if (!exists)
        {
            throw new IllegalStateException("Customer with ID " + customerId + " does not exist");
        }

        customerRepository.deleteById(customerId);
    }

    @Transactional
    public void updateCustomer(Long customerId, Customer customer)
    {
        Customer existingCustomer = customerRepository.findById(customerId)
                .orElseThrow(() -> new IllegalStateException("Customer ID not found: " + customerId));

        existingCustomer.setFirstName(customer.getFirstName());
        existingCustomer.setLastName(customer.getLastName());
        existingCustomer.setDateOfBirth(customer.getDateOfBirth());
    }
}
