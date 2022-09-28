package com.allane.leasingcontracts.it;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.init.ScriptUtils;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

@TestConfiguration
public class IntegrationTestConfiguration
{
    private static final String SQL_DATA_FILE = "/db/test-data.sql";

    @Autowired
    private DataSource dataSource;

    @PostConstruct
    public void initDB() throws SQLException
    {
        try (Connection con = dataSource.getConnection())
        {
            ScriptUtils.executeSqlScript(con, new ClassPathResource(SQL_DATA_FILE));
        }
    }
}
