package com.rasrov.similarproducts.config;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties({CacheConfiguration.class})
public class ApplicationConfiguration {
}
