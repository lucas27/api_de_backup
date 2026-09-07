package com.microservice.services.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Controller
@RestController
@RequestMapping("/services") 
public class ServicesController {
    
    @GetMapping("/upload")
    public String upload() {
        return "funciona";
    }
}
