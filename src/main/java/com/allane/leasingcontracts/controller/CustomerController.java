package com.allane.leasingcontracts.controller;

import com.allane.leasingcontracts.dto.CustomerDTO;
import com.allane.leasingcontracts.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = UrlConstants.CUSTOMERS_URL_V1)
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

    @GetMapping(path = "{customerId}")
    public ResponseEntity<CustomerDTO> getCustomer(@PathVariable("customerId") Long customerId)
    {
        ResponseEntity<CustomerDTO> responseEntity;
        try
        {
            CustomerDTO customer = customerService.getCustomer(customerId);
            responseEntity = new ResponseEntity<>(customer, HttpStatus.OK);
        }
        catch (IllegalArgumentException ex)
        {
            responseEntity = new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        catch (Exception e)
        {
            responseEntity = new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return responseEntity;
    }

    @PostMapping
    public ResponseEntity<CustomerDTO> addCustomer(@RequestBody CustomerDTO customerDTO)
    {
        CustomerDTO createdCustomerDto = customerService.addCustomer(customerDTO);
        return new ResponseEntity<>(createdCustomerDto, HttpStatus.CREATED);
    }

    @DeleteMapping(path = "{customerId}")
    public ResponseEntity<CustomerDTO> deleteCustomer(@PathVariable("customerId") Long customerId)
    {
        ResponseEntity<CustomerDTO> responseEntity;

        try
        {
            CustomerDTO deletedCustomer = customerService.deleteCustomer(customerId);
            responseEntity = new ResponseEntity<>(deletedCustomer, HttpStatus.OK);
        }
        catch (IllegalArgumentException ex)
        {
            responseEntity = new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        catch (Exception e)
        {
            responseEntity = new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return responseEntity;
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
