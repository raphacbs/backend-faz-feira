package com.coelho.fazfeira.behavior.product;

import com.coelho.fazfeira.dto.ProductRequest;
import com.coelho.fazfeira.mapper.ProductMapper;
import com.coelho.fazfeira.model.Product;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.Locale;

public class ProductBuilderGenerateCode implements ProductBuilderBehavior {
    private final Logger logger = LoggerFactory.getLogger(ProductBuilderGenerateCode.class);
    private final ProductMapper productMapper = ProductMapper.INSTANCE;

    @Override
    public Product build(ProductRequest productRequest) {
        logger.info("Converting product request to product...");
        Product product = this.productMapper.productRequestToProduct(productRequest);
        logger.info("Generating code to product");
        product.setCode(String.valueOf(new Date().getTime()));
        logger.info("Code generated {}", product.getCode());
        product.setDescription(product.getDescription().toUpperCase());
        product.setUpdateAt(LocalDateTime.now());
        product.setCreatedAt(LocalDateTime.now());
        return product;
    }
}
