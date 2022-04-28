package com.tomtenk.student;

import org.springframework.context.annotation.Configuration;

@Configuration
public class StudentConfig {

   /* @Bean
    CommandLineRunner commandLineRunner(StudentRepository repository) {
        return args -> {
            Student mariam = new Student("Mariam", "mariam.jamal@gmail.com",
                    LocalDate.of(2000, Month.APRIL, 22));
            Student alex = new Student("Alex", "alex.jamal@gmail.com",
                    LocalDate.of(2004, Month.APRIL, 22));
            if (System.getenv("DEPLOYMENT_ENV").equals("dev")) {
                repository.saveAll(List.of(mariam, alex));
            }
        };
    }*/
}
