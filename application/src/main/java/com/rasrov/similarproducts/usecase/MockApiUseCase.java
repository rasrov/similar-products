package com.rasrov.similarproducts.usecase;

import com.rasrov.similarproducts.domain.ProductDetail;
import com.rasrov.similarproducts.ports.MockApiClientPort;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.Set;

@Service
public class MockApiUseCase {

    private final MockApiClientPort mockApiClientPort;

    public MockApiUseCase(MockApiClientPort mockApiClientPort) {
        this.mockApiClientPort = mockApiClientPort;
    }

    @Cacheable("similarIds")
    public Mono<Set<Integer>> similarIds(final Integer productId) {
        return mockApiClientPort.similarIds(productId);
    }

    @Cacheable("productDetail")
    public Mono<ProductDetail> productDetail(final Integer productId) {
        return mockApiClientPort.productDetail(productId);
    }

}
