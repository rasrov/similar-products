package com.rasrov.similarproducts.usecase;

import com.rasrov.similarproducts.domain.ProductDetail;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Set;

@Service
public class FetchProductUseCase {

    private final MockApiUseCase mockApiUseCase;

    public FetchProductUseCase(MockApiUseCase mockApiUseCase) {
        this.mockApiUseCase = mockApiUseCase;
    }

    public List<ProductDetail> similarProducts(final Integer productId) {
        return productDetailResponseList(
                Objects.requireNonNull(mockApiUseCase.similarIds(productId))
        );
    }

    private List<ProductDetail> productDetailResponseList(final Set<Integer> similarProductsId) {
        return similarProductsId.stream()
                .map(mockApiUseCase::productDetail)
                .toList();
    }
}
