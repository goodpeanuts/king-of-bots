package com.kingofboss.backend.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ReturnStatus {
    @GetMapping("/api/get-status")
    public String getStatus() {
        System.out.println("backend ok");
        return "ok";
    }
}
