package com.fabrica.hutchisonspring;

import com.fabrica.hutchisonspring.config.ApplicationProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties({ApplicationProperties.class})
public class HutchisonSpringApplication {

    public static void main(String[] args) {
        SpringApplication.run(HutchisonSpringApplication.class, args);
    }

}
