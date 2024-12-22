package com.uees.mgra.producto;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class MsCompProductoApplication {
    public static void main(String[] args) {
        SpringApplication.run(MsCompProductoApplication.class, args);
    }

}
