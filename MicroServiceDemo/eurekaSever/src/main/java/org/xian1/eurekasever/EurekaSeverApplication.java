package org.xian1.eurekasever;

import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableEurekaServer
public class EurekaSeverApplication {

    public static void main(String[] args) {
        SpringApplication.run(EurekaSeverApplication.class, args);
        System.out.println("Eureka Server: http://localhost:9001");
    }
}
