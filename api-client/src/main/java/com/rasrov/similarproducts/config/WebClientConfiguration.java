package com.rasrov.similarproducts.config;

import io.netty.channel.ChannelOption;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;

import java.time.Duration;

@Configuration
public class WebClientConfiguration {

    private final MockApiConfiguration mockApiConfiguration;

    public WebClientConfiguration(MockApiConfiguration mockApiConfiguration) {
        this.mockApiConfiguration = mockApiConfiguration;
    }

    @Bean
    public WebClient mockApiWebClient() {
        final HttpClient httpClient = HttpClient.create()
                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 6000)
                .responseTimeout(Duration.ofMillis(6000));

        return WebClient.builder()
                .clientConnector(new ReactorClientHttpConnector(httpClient))
                .baseUrl(mockApiConfiguration.mockApiUrl())
                .build();
    }
}
