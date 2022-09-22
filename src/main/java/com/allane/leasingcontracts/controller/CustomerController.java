package com.allane.leasingcontracts.controller;

import com.allane.leasingcontracts.dto.CustomerDTO;
import com.allane.leasingcontracts.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "api/v1/customers")
public class CustomerController
{
    private final CustomerService customerService;

    @Autowired
    public CustomerController(CustomerService customerService)
    {
        this.customerService = customerService;
    }

    @GetMapping
    public List<CustomerDTO> getCustomers()
    {
        return customerService.getCustomers();
    }

    @PostMapping
    public void addCustomer(@RequestBody CustomerDTO customerDTO)
    {
        customerService.addCustomer(customerDTO);
    }

    @DeleteMapping(path = "{customerId}")
    public void deleteCustomer(@PathVariable("customerId") Long customerId)
    {
        customerService.deleteCustomer(customerId);
    }

    @PutMapping(path = "{customerId}")
    public void updateCustomer(
            @PathVariable("customerId") Long customerId,
            @RequestBody CustomerDTO customerDto
    )
    {
        customerService.updateCustomer(customerId, customerDto);
    }

}
