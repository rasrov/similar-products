package com.rasrov.similarproducts.ports;

import com.rasrov.similarproducts.domain.ProductDetail;

import java.util.List;

public interface FetchProductPort {

    List<ProductDetail> similarProducts(Integer productId);

}
