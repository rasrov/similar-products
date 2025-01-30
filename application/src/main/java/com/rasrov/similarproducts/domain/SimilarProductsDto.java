package com.rasrov.similarproducts.domain;

import java.util.List;

public record SimilarProductsDto(List<ProductDetailDto> productDetail, String errorMessage) {
}
