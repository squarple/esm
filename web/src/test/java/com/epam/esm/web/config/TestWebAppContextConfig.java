package com.epam.esm.web.config;

import com.epam.esm.persistence.repository.GiftCertificateRepository;
import com.epam.esm.persistence.repository.TagRepository;
import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.sql.DataSource;

import static org.mockito.Mockito.mock;

@Profile("test")
@Configuration
@EnableAutoConfiguration
@ComponentScan("com.epam.esm")
public class TestWebAppContextConfig implements WebMvcConfigurer {
    @Bean
    public DataSource dataSource() {
        return mock(BasicDataSource.class);
    }

    @Bean
    public GiftCertificateRepository giftCertificateDao() {
        return mock(GiftCertificateRepository.class);
    }

    @Bean
    public TagRepository tagDao() {
        return mock(TagRepository.class);
    }

    @Bean
    public MessageSource messageSource() {
        ReloadableResourceBundleMessageSource messageSource
                = new ReloadableResourceBundleMessageSource();
        messageSource.setBasename("classpath:messages");
        messageSource.setDefaultEncoding("UTF-8");
        return messageSource;
    }

    @Override
    @Bean
    public LocalValidatorFactoryBean getValidator() {
        LocalValidatorFactoryBean bean = new LocalValidatorFactoryBean();
        bean.setValidationMessageSource(messageSource());
        return bean;
    }
}
