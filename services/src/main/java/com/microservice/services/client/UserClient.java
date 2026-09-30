package com.microservice.services.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.microservice.services.dto.UserDto;

@FeignClient (name="user", url = "${api.host}:8081/user")
public interface UserClient {
    @GetMapping("/users/{id}")
    UserDto getUserName(@PathVariable("id") String id);
}
