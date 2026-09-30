package com.microservice.auth.controller;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.microservice.auth.dto.response.UserDto;
import com.microservice.auth.service.UserService;

import lombok.RequiredArgsConstructor;

@RestController 
@RequestMapping("/user")
@RequiredArgsConstructor 
public class UserController {
    private final UserService service;

    @RequestMapping(method = RequestMethod.GET, value = "/users/{id}")
    public UserDto userName(@PathVariable Long id) {
        return service.getUserName(id);
    }
}
