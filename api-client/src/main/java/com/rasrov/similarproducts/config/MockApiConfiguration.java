package com.rasrov.similarproducts.config;

import jakarta.validation.constraints.NotNull;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

@ConfigurationProperties(prefix = "mock-api")
@Validated
public record MockApiConfiguration(@NotNull String protocol,
                                   @NotNull String domain,
                                   @NotNull String port,
                                   @NotNull String resource) {

    public String mockApiUrl() {
        return String.format("%s://%s:%s/%s", protocol, domain, port, resource);
    }

}
