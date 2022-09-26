package com.kk.service.ucenter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@EnableDiscoveryClient
@ComponentScan(value = {"com.kk"})
public class ServiceUCenterApplication {
    public static void main(String[] args) {
        SpringApplication.run(ServiceUCenterApplication.class, args);
    }
}
