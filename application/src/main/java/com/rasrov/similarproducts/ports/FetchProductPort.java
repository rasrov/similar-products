package com.rasrov.similarproducts.ports;

import com.rasrov.similarproducts.domain.ProductDetailDto;

import java.util.List;

public interface FetchProductPort {

    List<ProductDetailDto> similarProducts(Integer productId);

}
