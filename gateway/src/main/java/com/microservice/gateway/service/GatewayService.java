package com.microservice.gateway.service;

import org.springframework.boot.security.oauth2.server.resource.autoconfigure.OAuth2ResourceServerProperties.Jwt;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ServerWebExchange;

import reactor.core.publisher.Mono;

@Service
public class GatewayService {
    private final JwtDecoder jwtDecoder;

    public GatewayService(JwtDecoder jwtDecoder) {
        this.jwtDecoder = jwtDecoder;
    }

    public Mono<Void> sendUserId(ServerWebExchange exchange, GatewayFilterChain chain) {
        String authHeader = exchange.getRequest().getHeaders().getFirst("Authorization");
                    
        String token = authHeader.substring(7);
        
        // Decodifica o token para pegar o id
        String userId = jwtDecoder.decode(token).getClaims().get("userId").toString();
        
        // injeta dentro do header o id, com a chave X-User-Id
        ServerHttpRequest mutateHeader = exchange
            .getRequest()
            .mutate()
            .header("X-User-Id", userId)
            .build();
        
        // retorna já com a modificação do header
        return chain.filter(exchange.mutate().request(mutateHeader).build());
    }
}
