package com.epam.esm.service.config;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Profile;

@TestConfiguration
@Profile("test")
@ComponentScan("com.epam.esm")
public class TestServiceConfig {

}
