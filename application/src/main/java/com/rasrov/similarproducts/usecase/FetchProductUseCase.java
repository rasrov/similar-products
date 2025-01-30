package com.rasrov.similarproducts.usecase;

import com.rasrov.similarproducts.domain.ProductDetail;
import com.rasrov.similarproducts.domain.ProductDetailDto;
import com.rasrov.similarproducts.domain.SimilarProductsDto;
import com.rasrov.similarproducts.ports.FetchProductPort;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.util.List;
import java.util.Objects;
import java.util.Set;

@Service
public class FetchProductUseCase implements FetchProductPort {

    private static final Logger log = LoggerFactory.getLogger(FetchProductUseCase.class);

    private final MockApiUseCase mockApiUseCase;

    public FetchProductUseCase(MockApiUseCase mockApiUseCase) {
        this.mockApiUseCase = mockApiUseCase;
    }

    @Override
    @Cacheable("similarProducts")
    public SimilarProductsDto similarProducts(final Integer productId) {
            final Set<Integer> similarIds = mockApiUseCase.similarIds(productId).block();

            return new SimilarProductsDto(productDetailResponseList(
                    Objects.requireNonNull(similarIds)), null);
    }

    private List<ProductDetailDto> productDetailResponseList(final Set<Integer> similarProductsId) {
        return Flux.fromIterable(similarProductsId)
                .flatMap(mockApiUseCase::productDetail)
                .map(productDetail -> buildProductDetailDto(Objects.requireNonNull(productDetail)))
                .collectList().block();
    }

    private ProductDetailDto buildProductDetailDto(final ProductDetail productDetail) {
        return new ProductDetailDto(productDetail.id(), productDetail.name(), productDetail.price(), productDetail.availability(), productDetail.errorMessage());
    }
}
