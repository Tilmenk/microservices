package com.tilmenk.priceService;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
@Slf4j
public class PriceServiceApp {
    public static void main(String[] args) {
        SpringApplication.run(PriceServiceApp.class, args);
    }

    @GetMapping(value = "/api/test")
    PriceResponse hello() {
        log.info("Get / ");
        return new PriceResponse(20);
    }
}