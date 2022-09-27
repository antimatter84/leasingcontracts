package com.allane.leasingcontracts.it;

import com.allane.leasingcontracts.controller.UrlConstants;
import org.junit.Ignore;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Ignore
@Sql({"/db/test-customers.sql"})
class CustomerIntegrationTest
    extends AbstractIntegrationTest
{

    @Test
    void shouldReturnCustomers() throws Exception
    {
        mockMvc.perform(get(UrlConstants.CUSTOMERS_URL_V1).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$", hasSize(2)));

    }
}
