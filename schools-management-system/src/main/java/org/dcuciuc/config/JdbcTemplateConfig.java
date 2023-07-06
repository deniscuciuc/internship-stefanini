package org.dcuciuc.config;

import liquibase.Liquibase;
import liquibase.integration.spring.SpringLiquibase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;

@Configuration
public class JdbcTemplateConfig {

    private final String URL = "spring.datasource.url";
    private final String USERNAME = "spring.datasource.username";
    private final String PASSWORD = "spring.datasource.password";

    private final Environment environment;

    public JdbcTemplateConfig(Environment environment) {
        this.environment = environment;
    }

    @Bean
    @Profile("!test")
    public DataSource dataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setUrl(environment.getProperty(URL));
        dataSource.setUsername(environment.getProperty(USERNAME));
        dataSource.setPassword(environment.getProperty(PASSWORD));
        return dataSource;
    }
}
