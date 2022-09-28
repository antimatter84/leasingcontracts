package com.allane.leasingcontracts.service;

import com.allane.leasingcontracts.dto.CustomerDTO;
import com.allane.leasingcontracts.model.Customer;
import com.allane.leasingcontracts.repository.CustomerRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
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

    public CustomerDTO getCustomer(Long customerId)
    {
        Optional<Customer> customer = customerRepository.findById(customerId);
        if (customer.isEmpty())
        {
            throw new IllegalArgumentException("No customer found with ID: " + customerId);
        }

        return mapper.map(customer, CustomerDTO.class);
    }

    public CustomerDTO addCustomer(CustomerDTO customerDto)
    {
        Customer customer = mapper.map(customerDto, Customer.class);
        boolean exists = customerRepository.existsByFirstNameAndLastNameAndDateOfBirth(
            customer.getFirstName(), customer.getLastName(), customer.getDateOfBirth());

        if (exists)
        {
            throw new IllegalStateException("Customer already exists.");
        }

        Customer savedCustomer = customerRepository.save(customer);
        return mapper.map(savedCustomer, CustomerDTO.class);
    }

    public CustomerDTO deleteCustomer(Long customerId)
    {
        Optional<Customer> customer = customerRepository.findById(customerId);
        if (customer.isEmpty())
        {
            throw new IllegalArgumentException("Customer with ID " + customerId + " does not exist");
        }

        customerRepository.deleteById(customerId);
        return mapper.map(customer, CustomerDTO.class);
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
