package com.example.mysaadmin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication(scanBasePackages = "com.example")
public class MySaAdminApplication {

    public static void main(String[] args) {
        SpringApplication.run(MySaAdminApplication.class, args);
    }

}
