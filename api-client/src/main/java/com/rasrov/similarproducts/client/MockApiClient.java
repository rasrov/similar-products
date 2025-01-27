package com.rasrov.similarproducts.client;

import com.rasrov.similarproducts.config.WebClientConfiguration;
import com.rasrov.similarproducts.domain.ProductDetail;
import com.rasrov.similarproducts.ports.MockApiClientPort;
import reactor.core.publisher.Mono;

import java.util.Set;
import java.util.stream.Collectors;

public class MockApiClient implements MockApiClientPort {

    private final WebClientConfiguration webClientConfiguration;

    public MockApiClient(WebClientConfiguration webClientConfiguration) {
        this.webClientConfiguration = webClientConfiguration;
    }

    @Override
    public Mono<Set<Integer>> similarIds(Integer productId) {
        return webClientConfiguration.mockApiWebClient().get()
                .uri(String.format("/%s/similarids", productId))
                .retrieve()
                .bodyToFlux(Integer.class)
                .collect(Collectors.toSet());
    }

    @Override
    public Mono<ProductDetail> productDetail(Integer productId) {
        return webClientConfiguration.mockApiWebClient().get()
                .uri(String.format("/%s", productId))
                .retrieve()
                .bodyToMono(ProductDetail.class);
    }
}
