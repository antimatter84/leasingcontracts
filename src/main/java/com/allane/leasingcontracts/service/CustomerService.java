package com.allane.leasingcontracts.service;

import com.allane.leasingcontracts.dto.CustomerDTO;
import com.allane.leasingcontracts.model.Customer;
import com.allane.leasingcontracts.repository.CustomerRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CustomerService
{
    private final CustomerRepository customerRepository;
    private final ModelMapper mapper;

    @Autowired
    public CustomerService(CustomerRepository customerRepository, ModelMapper mapper)
    {
        this.customerRepository = customerRepository;
        this.mapper = mapper;
    }

    public List<CustomerDTO> getCustomers()
    {
        return customerRepository.findAll()
                .stream()
                .map(c -> mapper.map(c, CustomerDTO.class))
                .collect(Collectors.toList());
    }

    public void addCustomer(CustomerDTO customerDto)
    {
        Customer customer = mapper.map(customerDto, Customer.class);
        boolean exists = customerRepository.existsByFirstNameAndLastNameAndDateOfBirth(
                customer.getFirstName(), customer.getLastName(), customer.getDateOfBirth());

        if (exists)
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
    public void updateCustomer(Long customerId, CustomerDTO customerDto)
    {
        Customer existingCustomer = customerRepository.findById(customerId)
                .orElseThrow(() -> new IllegalStateException("Customer ID not found: " + customerId));

        existingCustomer.setFirstName(customerDto.getFirstName());
        existingCustomer.setLastName(customerDto.getLastName());
        existingCustomer.setDateOfBirth(customerDto.getDateOfBirth());
    }
}
