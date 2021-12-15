package com.epam.esm.service.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

/**
 * The Service config.
 */
@Profile("dev")
@Configuration
@ComponentScan("com.epam.esm")
public class ServiceConfig {

}
