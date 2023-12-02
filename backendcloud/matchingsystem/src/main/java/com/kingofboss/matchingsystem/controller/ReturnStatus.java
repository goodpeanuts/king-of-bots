package com.kingofboss.matchingsystem.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ReturnStatus {
    @GetMapping("/matching/get-status")
    public String getStatus() {
        System.out.println("matching ok");
        return "ok";
    }
}
