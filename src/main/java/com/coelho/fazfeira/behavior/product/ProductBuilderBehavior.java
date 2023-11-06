package com.coelho.fazfeira.behavior.product;

import com.coelho.fazfeira.dto.ProductRequest;
import com.coelho.fazfeira.model.Product;

public interface ProductBuilderBehavior {
    Product build(ProductRequest productRequest);
}
