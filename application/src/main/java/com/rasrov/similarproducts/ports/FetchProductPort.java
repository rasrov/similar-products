package com.rasrov.similarproducts.ports;

import com.rasrov.similarproducts.domain.SimilarProductsDto;

public interface FetchProductPort {

    SimilarProductsDto similarProducts(Integer productId);

}
