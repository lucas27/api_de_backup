package com.microservice.services.configuration;

import org.springframework.cloud.openfeign.support.JsonFormWriter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration 
public class FeignMultipartConfig {
    @Bean
    public JsonFormWriter jsonFormWriter() {
        return new JsonFormWriter();
    }
}
