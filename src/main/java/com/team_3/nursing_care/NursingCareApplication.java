package com.team_3.nursing_care;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class NursingCareApplication {

    public static void main(String[] args) {
        SpringApplication.run(NursingCareApplication.class, args);
    }

}
