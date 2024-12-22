package com.uees.mgra.mscompuser;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class MsCompUserApplication {

    public static void main(String[] args) {
        SpringApplication.run(MsCompUserApplication.class, args);
    }

}
