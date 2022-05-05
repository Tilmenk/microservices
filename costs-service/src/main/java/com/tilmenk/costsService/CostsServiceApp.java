package com.tilmenk.costsService;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
@Slf4j
public class CostsServiceApp {
    public static void main(String[] args) {
        SpringApplication.run(CostsServiceApp.class, args);
        log.info("costs-service started !");
    }
}