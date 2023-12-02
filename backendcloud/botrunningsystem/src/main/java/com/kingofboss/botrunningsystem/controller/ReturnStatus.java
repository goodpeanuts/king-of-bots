package com.kingofboss.botrunningsystem.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ReturnStatus {
    @GetMapping("/bot/get-status")
    public String getStatus() {
        System.out.println("bot ok");
        return "ok";
    }
}
