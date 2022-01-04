package com.epam.esm.persistence.config;

import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.*;

import javax.sql.DataSource;

/**
 * The Persistence config.
 */
@Configuration
@Profile("dev")
@EnableAutoConfiguration
@ComponentScan("com.epam.esm")
@PropertySource("classpath:application.properties")
public class PersistenceConfig {
    @Value("${spring.datasource.driver-class-name}")
    private String driverName;

    @Value("${spring.datasource.url}")
    private String dbUrl;

    @Value("${spring.datasource.username}")
    private String username;

    @Value("${spring.datasource.password}")
    private String password;

    @Value("${spring.datasource.dbcp2.min-idle}")
    private Integer minIdle;

    @Value("${spring.datasource.dbcp2.max-idle}")
    private Integer maxIdle;

    /**
     * Data source.
     *
     * @return the data source
     */
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
