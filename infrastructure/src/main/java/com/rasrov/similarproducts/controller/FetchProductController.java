package com.rasrov.similarproducts.controller;

import com.rasrov.similarproducts.domain.SimilarProductsDto;
import com.rasrov.similarproducts.ports.FetchProductPort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("product")
public class FetchProductController {

    private final FetchProductPort fetchProductPort;

    public FetchProductController(FetchProductPort fetchProductPort) {
        this.fetchProductPort = fetchProductPort;
    }

    @GetMapping("/{productId}/similar")
    public ResponseEntity<SimilarProductsDto> getProduct(@PathVariable("productId") Integer productId) {
        return ResponseEntity.ok(fetchProductPort.similarProducts(productId));
    }
}
