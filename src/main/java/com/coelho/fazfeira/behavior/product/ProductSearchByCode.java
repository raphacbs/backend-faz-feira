package com.coelho.fazfeira.behavior.product;

import com.coelho.fazfeira.behavior.SearchBehavior;
import com.coelho.fazfeira.model.Product;
import com.coelho.fazfeira.repository.ProductRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import static com.coelho.fazfeira.constants.Params.PRODUCT_CODE;

public class ProductSearchByCode implements SearchBehavior<Product, ProductRepository> {
    @Override
    public Page<Product> searchPage(ProductRepository repository, Map<String, Object> params) {
        final Optional<Product> optionalProduct = repository.findById(String.valueOf(params.get(PRODUCT_CODE)));
        if(optionalProduct.isEmpty()){
            return Page.empty();
        }else{
            return new PageImpl<Product>(List.of(optionalProduct.get()));
        }
    }
}
