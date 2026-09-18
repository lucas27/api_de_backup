package com.microservice.gateway.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class GatewayConf {
    @Value("${gateway-api.host}")
    private String host;

    @Bean 
    public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
        return builder.routes()
        .route("auth", r -> r
            .path("/api/v1/auth/**")
            .filters(f -> f.rewritePath("/api/v1/auth/(?<segment>.*)", "/auth/${segment}"))
            .uri(host + ":8081"))
        .route("services", r -> r
            .path("/api/v1/services/**")
            .filters(f -> f.rewritePath("/api/v1/services/(?<segment>.*)", "/services/${segment}"))
            // .filters(f -> f.tokenRelay())
            .uri(host + ":8082"))
        .build();
    }
}
