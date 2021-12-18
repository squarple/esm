package com.epam.esm.web.config;

import com.epam.esm.persistence.dao.TagDao;
import com.epam.esm.persistence.dao.impl.TagDaoImpl;
import com.epam.esm.service.TagService;
import com.epam.esm.service.impl.TagServiceImpl;
import com.epam.esm.web.controller.TagController;
import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import javax.sql.DataSource;

import static org.mockito.Mockito.mock;

@Profile("test")
@Configuration
@ComponentScan(value = "com.epam.esm")
@EnableWebMvc
public class TestApplicationConfig {
    @Bean
    public DataSource dataSource() {
        return mock(BasicDataSource.class);
    }

    @Bean
    public TagDao tagDao() {
        return mock(TagDaoImpl.class);
    }

    @Bean
    public TagService tagService() {
        return mock(TagServiceImpl.class);
    }

    @Bean
    public TagController tagController() {
        return new TagController(tagService());
    }

}
