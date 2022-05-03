package com.tilmenk.identityService.config;

import com.tilmenk.identityService.model.Role;
import com.tilmenk.identityService.service.RoleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@Slf4j
public class RoleConfig {

    @Autowired
    private RoleService roleService;


    @Bean
    CommandLineRunner commandLineRunnerRole() {
        return args -> {
            try {
                roleService.saveRole(new Role("user_default"));
            } catch (Exception e) {
                log.error(e.getMessage());
            }
        };
    }
}
