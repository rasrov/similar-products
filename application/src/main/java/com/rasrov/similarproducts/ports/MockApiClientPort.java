package com.rasrov.similarproducts.ports;

import com.rasrov.similarproducts.domain.ProductDetail;
import reactor.core.publisher.Mono;

import java.util.Set;

public interface MockApiClientPort {

    Mono<Set<Integer>> similarIds(Integer productId);

    Mono<ProductDetail> productDetail(Integer productId);

}
