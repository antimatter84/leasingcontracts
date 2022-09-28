package com.allane.leasingcontracts.it;

import com.allane.leasingcontracts.dto.CustomerDTO;
import org.junit.Ignore;
import org.junit.jupiter.api.Test;
import org.springframework.test.web.servlet.MvcResult;

import java.time.LocalDate;

import static com.allane.leasingcontracts.controller.UrlConstants.CUSTOMERS_URL_V1;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Ignore
class CustomerIntegrationTest
    extends AbstractIntegrationTest
{

    @Test
    void shouldReturnCustomers() throws Exception
    {
        mockMvc.perform(get(CUSTOMERS_URL_V1).accept(APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$", hasSize(2)))
            .andDo(result -> System.out.println(result.getResponse().getContentAsString()));

    }

    @Test
    void shouldReturnSingleCustomer() throws Exception
    {
        String id = "2";

        MvcResult mvcResult =
            mockMvc.perform(get(CUSTOMERS_URL_V1 + "/{id}", id).accept(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        CustomerDTO customer = deserialize(mvcResult, CustomerDTO.class);
        assertThat(customer.getFirstName()).isEqualTo("Keanu");
    }

    @Test
    void shouldSaveNewCustomer() throws Exception
    {
        CustomerDTO stewart = new CustomerDTO(
            "Patrick", "Stewart", LocalDate.of(1940, 7, 13));

        MvcResult postResult =
            mockMvc.perform(post(CUSTOMERS_URL_V1).contentType(APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(stewart)))
                .andExpect(status().isCreated())
                .andDo(result -> System.out.println(result.getResponse().getContentAsString()))
                .andReturn();

        CustomerDTO savedStewart = deserialize(postResult, CustomerDTO.class);

        assertThat(savedStewart)
            .usingRecursiveComparison()
            .ignoringFields("id")
            .isEqualTo(stewart);

    }

    @Test
    void shouldDeleteCustomer() throws Exception
    {
        String idToDelete = "1";

        MvcResult deleteResult =
            mockMvc.perform(delete(CUSTOMERS_URL_V1 + "/{id}", idToDelete)
                    .contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        CustomerDTO deletedCustomer = deserialize(deleteResult, CustomerDTO.class);
        assertThat(deletedCustomer.getLastName()).isEqualTo("Schwarzenegger");

    }

    @Test
    void shouldFailWithNotFound_whenDeletingCustomerWithUnknownID() throws Exception
    {
        String idToDelete = "999";

        mockMvc.perform(delete(CUSTOMERS_URL_V1 + "/{id}", idToDelete)
                .contentType(APPLICATION_JSON))
            .andExpect(status().isNotFound());
    }
}
