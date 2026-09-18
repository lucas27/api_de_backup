package com.microservice.auth.controller;

import java.security.interfaces.RSAPublicKey;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.microservice.auth.dto.request.CreateUserDto;
import com.microservice.auth.dto.request.LoginUserDto;
import com.microservice.auth.dto.response.TokenDto;
import com.microservice.auth.service.UserService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor 
public class AuthController {
    private final UserService service;

    @Value("${api.public-key}")
    private RSAPublicKey publicKey;

    @RequestMapping(method = RequestMethod.POST, value = "/sign-up")
    public ResponseEntity<String> register(@RequestBody @Valid CreateUserDto dto) {
        String message = service.createUser(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(message);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/sign-in")
    public ResponseEntity<TokenDto> login(@RequestBody @Valid LoginUserDto dto) {

        TokenDto token = service.login(dto);
        return ResponseEntity.status(HttpStatus.OK).body(token);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/teste")
    public ResponseEntity<String> teste() {

        return ResponseEntity.status(HttpStatus.OK).body("autorizado");
    }


    @RequestMapping(method = RequestMethod.POST, value = "/validator")
    public ResponseEntity<Map<String, String>> validatorToken(@RequestHeader("Authorization") String token) {
        return ResponseEntity.status(HttpStatus.OK).body(service.validationToken(token));
    }

}
