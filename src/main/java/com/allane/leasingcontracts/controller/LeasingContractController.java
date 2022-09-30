package com.allane.leasingcontracts.controller;

import com.allane.leasingcontracts.dto.LeasingContractDTO;
import com.allane.leasingcontracts.dto.NewContractDTO;
import com.allane.leasingcontracts.service.LeasingContractService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = UrlConstants.CONTRACTS_V1_URL)
public class LeasingContractController
{
    private final LeasingContractService contractService;

    @Autowired
    public LeasingContractController(LeasingContractService contractService)
    {
        this.contractService = contractService;
    }

    @GetMapping
    public List<LeasingContractDTO> getContracts()
    {
        return contractService.getContracts();
    }

    @GetMapping(path = "{contractId}")
    public ResponseEntity<LeasingContractDTO> getContract(@PathVariable("contractId") Long id)
    {
        ResponseEntity<LeasingContractDTO> responseEntity;
        try
        {
            LeasingContractDTO contractDto = contractService.getContract(id);
            responseEntity = new ResponseEntity<>(contractDto, HttpStatus.OK);
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
    public ResponseEntity<LeasingContractDTO> addContract(@RequestBody NewContractDTO contract)
    {
        ResponseEntity<LeasingContractDTO> responseEntity;
        try
        {
            LeasingContractDTO createdContractDto = contractService.addContract(contract);
            responseEntity = new ResponseEntity<>(createdContractDto, HttpStatus.CREATED);
        }
        catch (IllegalArgumentException ae)
        {
            // we could include an object in the response containing the exception message
            responseEntity = new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        catch (Exception e)
        {
            responseEntity = new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return responseEntity;
    }

    @DeleteMapping(path = "{contractId}")
    public ResponseEntity<LeasingContractDTO> deleteContract(@PathVariable("contractId") Long contractId)
    {
        ResponseEntity<LeasingContractDTO> responseEntity;

        try
        {
            LeasingContractDTO deletedContract = contractService.deleteContract(contractId);
            responseEntity = new ResponseEntity<>(deletedContract, HttpStatus.OK);
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

    @PutMapping(path = "{contractId}")
    public void updateCustomer(
        @PathVariable("customerId") Long contractId,
        @RequestBody NewContractDTO contractDTO
    )
    {
        contractService.updateContract(contractId, contractDTO);
    }
}
