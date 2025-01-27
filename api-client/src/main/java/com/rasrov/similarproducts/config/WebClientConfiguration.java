package com.rasrov.similarproducts.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfiguration {

    private final MockApiConfiguration mockApiConfiguration;

    public WebClientConfiguration(MockApiConfiguration mockApiConfiguration) {
        this.mockApiConfiguration = mockApiConfiguration;
    }

    @Bean
    public WebClient mockApiWebClient() {
        return WebClient.builder()
                .baseUrl(mockApiConfiguration.mockApiUrl())
                .build();
    }
}
