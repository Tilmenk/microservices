package com.tilmenk.priceService;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class PriceServiceApp {
    public static void main(String[] args) {
        SpringApplication.run(PriceServiceApp.class, args);
    }

    @GetMapping(value = "/")
    String hello() {
        return "Hello World!";
    }
}