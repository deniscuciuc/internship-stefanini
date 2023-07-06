package org.dcuciuc;

import liquibase.integration.spring.SpringLiquibase;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;

@TestConfiguration
@PropertySource("application-test.properties")
public class H2DataSource {
    private final String URL = "spring.datasource.url";
    private final String USERNAME = "spring.datasource.username";
    private final String PASSWORD = "spring.datasource.password";

    private final Environment environment;

    public H2DataSource(Environment environment) {
        this.environment = environment;
    }


    @Bean
    @Profile("test")
    public DataSource dataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setUrl(environment.getProperty(URL));
        dataSource.setUsername(environment.getProperty(USERNAME));
        dataSource.setPassword(environment.getProperty(PASSWORD));
        return dataSource;
    }
}
