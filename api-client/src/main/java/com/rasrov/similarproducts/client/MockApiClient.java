package com.rasrov.similarproducts.client;

import com.rasrov.similarproducts.config.WebClientConfiguration;
import com.rasrov.similarproducts.domain.ProductDetail;
import com.rasrov.similarproducts.ports.MockApiClientPort;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.Set;
import java.util.stream.Collectors;

@Service
public class MockApiClient implements MockApiClientPort {

    private final WebClientConfiguration webClientConfiguration;

    public MockApiClient(WebClientConfiguration webClientConfiguration) {
        this.webClientConfiguration = webClientConfiguration;
    }

    @Override
    @CircuitBreaker(name = "mockService", fallbackMethod = "fallbackSimilarIds")
    public Mono<Set<Integer>> similarIds(Integer productId) {
        return webClientConfiguration.mockApiWebClient().get()
                .uri(String.format("/%s/similarids", productId))
                .retrieve()
                .bodyToFlux(Integer.class)
                .collect(Collectors.toSet())
                .doOnError(ex -> {
                    throw new RuntimeException(String.format("External service error %s", ex.getMessage()));
                });
    }

    @Override
    @CircuitBreaker(name = "mockService", fallbackMethod = "fallbackProductDetail")
    public Mono<ProductDetail> productDetail(Integer productId) {
        return webClientConfiguration.mockApiWebClient().get()
                .uri(String.format("/%s", productId))
                .retrieve()
                .bodyToMono(ProductDetail.class)
                .doOnError(ex -> {
                    throw new RuntimeException(String.format("External service error %s", ex.getMessage()));
                });
    }

    private Mono<Set<Integer>> fallbackSimilarIds(final Integer productIds, final Throwable throwable) {
        System.out.printf("Fallback error handled %s", throwable.getMessage());
        return Mono.just(Set.of());
    }

    private Mono<ProductDetail> fallbackProductDetail(final Integer productIds, final Throwable throwable) {
        System.out.printf("Fallback error handled %s", throwable.getMessage());
        return Mono.just(new ProductDetail("-1", "fallback", 0.0, false));
    }
}
