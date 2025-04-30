package com.example.authentication.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/test")
public class TestController {
    @RequestMapping("/all")
    public String allAccess() {
        return "Public Content.";
    }

    @RequestMapping("/user")
    public String userAccess() {
        return "User Content.";
    }

}
