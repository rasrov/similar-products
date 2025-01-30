package com.rasrov.similarproducts.usecase;

import com.rasrov.similarproducts.domain.ProductDetail;
import com.rasrov.similarproducts.domain.ProductDetailDto;
import com.rasrov.similarproducts.domain.SimilarProductsDto;
import com.rasrov.similarproducts.exception.ApplicationException;
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
        try {
            Set<Integer> similarIds = mockApiUseCase.similarIds(productId).block();

            return new SimilarProductsDto(productDetailResponseList(
                    Objects.requireNonNull(similarIds)), null);
        } catch (Exception e) {
            log.error("Error trying to request for similar ids: {}", e.getMessage());
            return new SimilarProductsDto(List.of(), e.getMessage());
        }
    }

    private List<ProductDetailDto> productDetailResponseList(final Set<Integer> similarProductsId) {
        try {
            return Flux.fromIterable(similarProductsId)
                    .flatMap(mockApiUseCase::productDetail)
                    .map(productDetail -> buildProductDetailDto(Objects.requireNonNull(productDetail)))
                    .collectList().block();
        } catch (Exception e) {
            log.error("Error trying to request for product detail: {}", e.getMessage());
            throw new ApplicationException(e.getMessage());
        }
    }

    private ProductDetailDto buildProductDetailDto(final ProductDetail productDetail) {
        return new ProductDetailDto(productDetail.id(), productDetail.name(), productDetail.price(), productDetail.availability(), productDetail.errorMessage());
    }
}
