package com.rasrov.similarproducts.client;

import com.rasrov.similarproducts.config.WebClientConfiguration;
import com.rasrov.similarproducts.domain.ProductDetail;
import com.rasrov.similarproducts.ports.MockApiClientPort;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.Set;
import java.util.stream.Collectors;

@Service
public class MockApiClient implements MockApiClientPort {

    private static final Logger log = LoggerFactory.getLogger(MockApiClient.class);

    private final WebClientConfiguration webClientConfiguration;

    public MockApiClient(final WebClientConfiguration webClientConfiguration) {
        this.webClientConfiguration = webClientConfiguration;
    }

    @Override
    @CircuitBreaker(name = "mockService", fallbackMethod = "fallbackSimilarIds")
    public Mono<Set<Integer>> similarIds(final Integer productId) {
        return webClientConfiguration.mockApiWebClient().get()
                .uri(String.format("/%s/similarids", productId))
                .retrieve()
                .bodyToFlux(Integer.class)
                .collect(Collectors.toSet());
    }

    @Override
    @CircuitBreaker(name = "mockService", fallbackMethod = "fallbackProductDetail")
    public Mono<ProductDetail> productDetail(final Integer productId) {
        return webClientConfiguration.mockApiWebClient().get()
                .uri(String.format("/%s", productId))
                .retrieve()
                .bodyToMono(ProductDetail.class);
    }

    private Mono<Set<Integer>> fallbackSimilarIds(final Integer productId, final Throwable throwable) {
        log.error("Fallback similar ids error handled {}", throwable.getMessage());
        return Mono.just(Set.of());
    }

    private Mono<ProductDetail> fallbackProductDetail(final Integer productId, final Throwable throwable) {
        log.error("Fallback product detail error handled {}", throwable.getMessage());
        return Mono.just(new ProductDetail(productId, null, null, null, String.format("%s: %s", throwable.getCause(), throwable.getMessage())));
    }
}
