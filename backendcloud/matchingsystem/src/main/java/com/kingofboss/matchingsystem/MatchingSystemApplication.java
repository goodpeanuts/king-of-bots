package com.kingofboss.matchingsystem;


import com.kingofboss.matchingsystem.service.MatchingService;
import com.kingofboss.matchingsystem.service.impl.MatchingServiceImpl;
import com.kingofboss.matchingsystem.service.impl.utils.MatchingPool.MatchingPool;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MatchingSystemApplication {
    public static void main(String[] args) {
        MatchingServiceImpl.matchingPool.start(); // 在matchingsystem启动时启动匹配线程
        SpringApplication.run(MatchingSystemApplication.class, args);
    }
}