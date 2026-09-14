package com.microservice.auth.service;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.microservice.auth.dto.response.TokenDto;

import jakarta.security.auth.message.AuthException;

@Service 
public class JwtService {

    private final JwtEncoder encoder;
    private final JwtDecoder decoder;

    public JwtService(JwtEncoder encoder, JwtDecoder decoder) {
        this.encoder = encoder;
        this.decoder = decoder;
    }

    public String formattedDataAndTime(Instant now) {
        ZoneId fusoBR = ZoneId.of("America/Sao_Paulo");
        ZonedDateTime timeZone = now.atZone(fusoBR);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");

        return timeZone.format(formatter);
    }

    public Map<String, String> generatedAcessToken(Authentication authentication) {
        Instant now = Instant.now();
        
        String scope = authentication.getAuthorities()
        .stream()
        .map(authorities -> authorities.getAuthority())
        .collect(Collectors.joining(" "));
        
        JwtClaimsSet accessClaims = JwtClaimsSet.builder()
        .issuer("api")
        .issuedAt(now)
        .expiresAt(now.plus(5, ChronoUnit.MINUTES))
        .subject(authentication.getName())
        .claim("scope", scope)
        .build();
        
        String accessToken = this.encoder.encode(JwtEncoderParameters.from(accessClaims)).getTokenValue();     
        
        return Map.of(
            "Access token", accessToken,
            "expiry", formattedDataAndTime(now.plus(5, ChronoUnit.MINUTES))
        );
    }
    
    public Map<String, String> generatedRefreshToken(Authentication authentication) {
        Instant now = Instant.now();
        
        JwtClaimsSet refreshClaims = JwtClaimsSet.builder()
            .issuer("api")
            .issuedAt(now)
            .expiresAt(now.plus(7, ChronoUnit.DAYS))
            .subject(authentication.getName())
            .claim("scope", "REFRESH_TOKEN")
            .build();
            
        String refreshToken = this.encoder.encode(JwtEncoderParameters.from(refreshClaims)).getTokenValue();
            
        return Map.of(
            "Refresh token", refreshToken,
            "expiry", formattedDataAndTime(now.plus(7, ChronoUnit.DAYS))
        );
    }

    public Map<String, String> generatedNewAccessToken(String token) {
        Instant now = Instant.now();
        Jwt jwt = decoder.decode(token);

        JwtClaimsSet accessClaims = JwtClaimsSet.builder()
        .issuer("api")
        .issuedAt(now)
        .expiresAt(now.plus(5, ChronoUnit.MINUTES))
        .subject(jwt.getSubject())
        .claim("scope", jwt.getClaims().get("scope").toString().replace(" ", ""))
        .build();
        
        String accessToken = this.encoder.encode(JwtEncoderParameters.from(accessClaims)).getTokenValue();     
        
        return Map.of(
            "Access token", accessToken,
            "expiry", formattedDataAndTime(now.plus(5, ChronoUnit.MINUTES))
        );
    }

}
