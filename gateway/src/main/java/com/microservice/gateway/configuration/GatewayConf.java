package com.microservice.gateway.configuration;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GatewayConf {
    @Bean 
    public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
        return builder.routes()
        .route("services", r -> r.path("/services/**")
            .uri("http://localhost:8081"))
        .build();
    }
}
