package com.allane.leasingcontracts.it;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.containers.MariaDBContainer;

import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@SpringBootTest(webEnvironment = RANDOM_PORT)
@AutoConfigureMockMvc
@ActiveProfiles("it")
public abstract class AbstractIntegrationTest
{
    private static final MariaDBContainer<?> mariadb;

    static
    {
        mariadb = new MariaDBContainer<>("mariadb:10.6")
            .withUsername("root")
            .withPassword("")
            .withDatabaseName("leasing-test");
        mariadb.start();
    }

    @Autowired
    protected MockMvc mockMvc;

    @DynamicPropertySource
    static void properties(DynamicPropertyRegistry registry)
    {
        registry.add("spring.datasource.url", mariadb::getJdbcUrl);
        registry.add("spring.datasource.username", mariadb::getUsername);
        registry.add("spring.datasource.password", mariadb::getPassword);
    }

    //    @BeforeAll
    //    static void beforeAll()
    //    {
    //        JdbcDatabaseDelegate delegate = new JdbcDatabaseDelegate(mariadb, "");
    //        ScriptUtils.runInitScript(delegate, "db/test-customers.sql");
    //    }
}
