package com.rasrov.similarproducts.domain;

public record ProductDetailDto(Integer id, String name, Double price, Boolean availability, String errorMessage) {
}
