package com.itplanet.contest.config;

import net.kaczmarzyk.spring.data.jpa.web.SpecificationArgumentResolver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.validation.beanvalidation.MethodValidationPostProcessor;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
@EnableJpaRepositories(basePackages = "com.itplanet.contest.repository")
@EnableWebMvc
public class MyConfig implements WebMvcConfigurer {

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
        //specification handler
        argumentResolvers.add(new SpecificationArgumentResolver());
    }

    @Bean
    public MethodValidationPostProcessor methodValidationPostProcessor() {
        //validation handler
        return new MethodValidationPostProcessor();
    }
}
