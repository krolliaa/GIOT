package com.kk.service.statistic;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableDiscoveryClient
@ComponentScan(value = {"com.kk"})
@EnableFeignClients(basePackages = {"com.kk.feign_api.client"})
@EnableScheduling
public class ServiceStatisticApplication {
    public static void main(String[] args) {
        SpringApplication.run(ServiceStatisticApplication.class, args);
    }
}