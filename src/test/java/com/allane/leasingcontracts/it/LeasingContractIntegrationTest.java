package com.allane.leasingcontracts.it;

import com.allane.leasingcontracts.dto.LeasingContractDTO;
import com.allane.leasingcontracts.dto.NewContractDTO;
import org.assertj.core.data.Percentage;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.test.web.servlet.MvcResult;

import java.math.BigDecimal;

import static com.allane.leasingcontracts.controller.UrlConstants.CONTRACTS_V1_URL;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class LeasingContractIntegrationTest
    extends AbstractIntegrationTest
{
    @Test
    @Order(1)
    void shouldReturnContracts() throws Exception
    {
        mockMvc.perform(get(CONTRACTS_V1_URL).accept(APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$", hasSize(1)))
            .andExpect(jsonPath("$[0].contractNumber").value("LC0001"))
            .andExpect(jsonPath("$[0].fullName").value(containsString("Schwarzenegger")))
            .andExpect(jsonPath("$[0].vehicleDescription").value(containsString("Mercedes")))
            .andDo(result -> System.out.println(result.getResponse().getContentAsString()));

    }

    @Test
    @Order(2)
    void shouldReturnSingleContract() throws Exception
    {
        String id = "1";

        MvcResult result = mockMvc.perform(get(CONTRACTS_V1_URL + "/{id}", id).accept(APPLICATION_JSON))
            .andExpect(status().isOk())
            .andDo(r -> System.out.println(r.getResponse().getContentAsString()))
            .andReturn();

        LeasingContractDTO contract = deserialize(result, LeasingContractDTO.class);

        assertThat(contract.getContractNumber()).isEqualTo("LC0001");
        assertThat(contract.getFullName()).contains("Schwarzenegger");
        assertThat(contract.getVehicleDescription()).contains("Mercedes");
    }

    @Test
    @Order(3)
    void shouldFailWithNotFound_whenContractIDIsUnknown() throws Exception
    {
        String id = "999";

        mockMvc.perform(get(CONTRACTS_V1_URL + "/{id}", id).accept(APPLICATION_JSON))
            .andExpect(status().isNotFound());
    }

    @Test
    @Order(4)
    void shouldCreateLeasingContract() throws Exception
    {
        NewContractDTO contract = new NewContractDTO(
            "LC0002", BigDecimal.valueOf(99), 2L, 2L);

        MvcResult postResult = mockMvc
            .perform(post(CONTRACTS_V1_URL).contentType(APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(contract)))
            .andExpect(status().isCreated())
            .andDo(result -> System.out.println(result.getResponse().getContentAsString()))
            .andReturn();

        LeasingContractDTO savedContract = deserialize(postResult, LeasingContractDTO.class);

        assertThat(savedContract.getId()).isGreaterThan(1L);
        assertThat(savedContract.getContractNumber()).isEqualTo(contract.getContractNumber());
        assertThat(savedContract.getRate()).isCloseTo(contract.getLeasingRate(), Percentage.withPercentage(1));
        assertThat(savedContract.getFullName()).containsAnyOf("Keanu", "Reeves");
        assertThat(savedContract.getVehicleDescription()).containsAnyOf("Audi", "A3");


    }
}
