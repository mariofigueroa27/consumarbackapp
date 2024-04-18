package com.fabrica.hutchisonspring.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories("com.fabrica.hutchisonspring.repository")
public class DatabaseConfiguration {
}
