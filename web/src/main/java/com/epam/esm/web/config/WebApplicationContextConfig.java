package com.epam.esm.web.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@ComponentScan("com.epam.esm")
@EnableWebMvc
public class WebApplicationContextConfig implements WebMvcConfigurer {

}
