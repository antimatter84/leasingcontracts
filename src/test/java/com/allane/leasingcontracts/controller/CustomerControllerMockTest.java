package com.allane.leasingcontracts.controller;

import com.allane.leasingcontracts.dto.CustomerDTO;
import com.allane.leasingcontracts.service.CustomerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CustomerController.class)
class CustomerControllerMockTest
{

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CustomerService service;

    private final CustomerDTO arnold = new CustomerDTO(
        "Arnold",
        "Schwarzenegger",
        LocalDate.of(1947, 7, 30));
    private final CustomerDTO keanu = new CustomerDTO(
        "Keanu",
        "Reeves",
        LocalDate.of(1964, 9, 2));

    @BeforeEach
    void returnSomeCustomers()
    {
        List<CustomerDTO> someCustomers = List.of(arnold, keanu);
        given(service.getCustomers()).willReturn(someCustomers);
    }

    @Test
    void shouldReturnTwoCustomersFromService() throws Exception
    {
        this.mockMvc.perform(get("/api/v1/customers"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$", hasSize(2)));
    }

    @Test
    void shouldReturnTheInformationOfCustomers() throws Exception
    {
        this.mockMvc.perform(get("/api/v1/customers"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$[0].firstName").value(arnold.getFirstName()))
            .andExpect(jsonPath("$[0].lastName").value(arnold.getLastName()))
            .andExpect(jsonPath("$[0].dateOfBirth").value(arnold.getDateOfBirth().toString()))
            .andExpect(jsonPath("$[1].firstName").value(keanu.getFirstName()))
            .andExpect(jsonPath("$[1].lastName").value(keanu.getLastName()))
            .andExpect(jsonPath("$[1].dateOfBirth").value(keanu.getDateOfBirth().toString()));
    }
}
