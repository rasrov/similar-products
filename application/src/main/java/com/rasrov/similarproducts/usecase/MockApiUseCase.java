package com.rasrov.similarproducts.usecase;

import com.rasrov.similarproducts.domain.ProductDetail;
import com.rasrov.similarproducts.ports.MockApiClientPort;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class MockApiUseCase {

    private final MockApiClientPort mockApiClientPort;

    public MockApiUseCase(MockApiClientPort mockApiClientPort) {
        this.mockApiClientPort = mockApiClientPort;
    }

    @CircuitBreaker(name = "externalService", fallbackMethod = "fallbackSimilarIds")
    public Set<Integer> similarIds(final Integer productId) {
        return mockApiClientPort.similarIds(productId).block();
    }

    @CircuitBreaker(name = "externalService", fallbackMethod = "fallbackProductDetail")
    public ProductDetail productDetail(final Integer productId) {
        return mockApiClientPort.productDetail(productId).block();
    }

    private String fallbackSimilarIds(final Throwable throwable) {
        return String.format("Error calling external API (similar ids): %s", throwable.getMessage());
    }

    private String fallbackProductDetail(final Throwable throwable) {
        return String.format("Error calling external API (product detail): %s", throwable.getMessage());
    }


}
