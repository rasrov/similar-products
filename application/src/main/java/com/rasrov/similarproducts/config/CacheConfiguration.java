package com.rasrov.similarproducts.config;

import com.github.benmanes.caffeine.cache.Caffeine;
import jakarta.validation.constraints.NotNull;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.validation.annotation.Validated;

import java.util.concurrent.TimeUnit;

@ConfigurationProperties(prefix = "cache")
@Validated
public record CacheConfiguration(@NotNull Integer duration,
                                 @NotNull Integer maxSize) {

    @Bean
    public Caffeine<Object, Object> caffeineConfig() {
        return Caffeine.newBuilder()
                .expireAfterWrite(duration, TimeUnit.MINUTES)
                .maximumSize(maxSize);
    }
}
