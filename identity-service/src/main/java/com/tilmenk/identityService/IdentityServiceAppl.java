package com.tilmenk.identityService;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
//@PropertySource("classpath:application-${spring.profiles.active:default}
// .properties")
public class IdentityServiceAppl {

    public static void main(String[] args) {
        SpringApplication.run(IdentityServiceAppl.class, args);
        System.out.println("App started !");
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
