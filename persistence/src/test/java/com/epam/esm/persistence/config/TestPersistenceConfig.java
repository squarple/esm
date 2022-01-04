package com.epam.esm.persistence.config;

import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.*;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManager;
import javax.sql.DataSource;

@Profile("test")
@Configuration
@EnableAutoConfiguration
@ComponentScan(value = "com.epam.esm")
//@EnableTransactionManagement
@PropertySource("classpath:application-test.properties")
public class TestPersistenceConfig {
    @Value("${jdbc.driverClassName}")
    private String driverName;

    @Value("${jdbc.url}")
    private String dbUrl;

    @Value("${jdbc.username}")
    private String username;

    @Value("${jdbc.password}")
    private String password;

    @Value("${jdbc.min.idle}")
    private Integer minIdle;

    @Value("${jdbc.max.idle}")
    private Integer maxIdle;

    @Bean
    public DataSource dataSource() {
        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setDriverClassName(driverName);
        dataSource.setUrl(dbUrl);
        dataSource.setUsername(username);
        dataSource.setPassword(password);
        dataSource.setMinIdle(minIdle);
        dataSource.setMaxIdle(maxIdle);
        return dataSource;
    }
}
