package com.allane.leasingcontracts.it;

import org.junit.jupiter.api.Test;
import org.springframework.test.web.servlet.MvcResult;

import static com.allane.leasingcontracts.controller.UrlConstants.VEHICLES_V1_URL;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class VehicleIntegrationTest
    extends AbstractIntegrationTest
{

    @Test
    void shouldReturnUniqueVehicleBrands() throws Exception
    {
        final String brandPath = "/brands";
        final String[] expectedBrands = {"Mercedes", "Audi"};

        MvcResult mvcResult = mockMvc.perform(get(VEHICLES_V1_URL + brandPath).accept(APPLICATION_JSON))
            .andExpect(status().isOk())
            .andDo(result -> System.out.println(result.getResponse().getContentAsString()))
            .andReturn();

        String[] actualBrands = objectMapper.readValue(
            mvcResult.getResponse().getContentAsString(), String[].class);

        assertThat(actualBrands)
            .hasSameSizeAs(expectedBrands)
            .containsExactlyInAnyOrder(expectedBrands);

    }
}
