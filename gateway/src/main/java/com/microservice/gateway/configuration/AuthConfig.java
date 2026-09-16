package com.microservice.gateway.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

@Configuration
@EnableWebFluxSecurity   
public class AuthConfig {
    @Bean
    public SecurityWebFilterChain securityFilterChain(ServerHttpSecurity http){
        return http
        .csrf(csrf -> csrf.disable())
        .authorizeExchange(r -> r
            .pathMatchers("/api/v1/auth/**").permitAll()
            .anyExchange().authenticated()
        )
        .oauth2ResourceServer(oauth -> oauth.jwt(Customizer.withDefaults()))
        .build();
    }
    
}
