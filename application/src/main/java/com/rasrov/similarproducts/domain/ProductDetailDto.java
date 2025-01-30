package com.rasrov.similarproducts.domain;

public record ProductDetailDto(String id, String name, Double price, Boolean availability, String errorMessage) {
}
