package com.epam.esm.service.config;

import org.apache.commons.dbcp2.BasicDataSource;
import org.mockito.Mockito;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import javax.sql.DataSource;

@Configuration
@ComponentScan("com.epam.esm")
public class TestServiceConfig {
//    @Bean
//    public DataSource dataSource() {
//        return Mockito.mock(BasicDataSource.class);
//    }
}
