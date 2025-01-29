package com.rasrov.similarproducts.usecase;

import com.rasrov.similarproducts.domain.ProductDetail;
import com.rasrov.similarproducts.domain.ProductDetailDto;
import com.rasrov.similarproducts.ports.FetchProductPort;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.util.List;
import java.util.Objects;
import java.util.Set;

@Service
public class FetchProductUseCase implements FetchProductPort {

    private final MockApiUseCase mockApiUseCase;

    public FetchProductUseCase(MockApiUseCase mockApiUseCase) {
        this.mockApiUseCase = mockApiUseCase;
    }

    @Override
    public List<ProductDetailDto> similarProducts(final Integer productId) {
        return productDetailResponseList(
                Objects.requireNonNull(mockApiUseCase.similarIds(productId).block())
        );
    }

    private List<ProductDetailDto> productDetailResponseList(final Set<Integer> similarProductsId) {
        return Flux.fromIterable(similarProductsId)
                .flatMap(mockApiUseCase::productDetail)
                .map(productDetail -> buildProductDetailDto(Objects.requireNonNull(productDetail)))
                .collectList().block();
    }

    private ProductDetailDto buildProductDetailDto(final ProductDetail productDetail) {
        return new ProductDetailDto(productDetail.id(), productDetail.name(), productDetail.price(), productDetail.availability());
    }
}
