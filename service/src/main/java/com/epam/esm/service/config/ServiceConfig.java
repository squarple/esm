package com.epam.esm.service.config;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * The Service config.
 */
@Configuration
@Profile("dev")
@EnableAutoConfiguration
@EnableTransactionManagement
@ComponentScan("com.epam.esm")
public class ServiceConfig {

}
