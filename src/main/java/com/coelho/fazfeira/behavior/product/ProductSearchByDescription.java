package com.coelho.fazfeira.behavior.product;

import com.coelho.fazfeira.behavior.SearchBehavior;
import com.coelho.fazfeira.constants.Params;
import com.coelho.fazfeira.model.Product;
import com.coelho.fazfeira.repository.ProductRepository;
import org.springframework.data.domain.Page;

import java.util.Map;

public class ProductSearchByDescription implements SearchBehavior<Product, ProductRepository> {
    @Override
    public Page<Product> searchPage(ProductRepository repository, Map<String, Object> params) {
       return repository.findByDescriptionIgnoreCaseContaining(getPageable(params),
                String.valueOf(params.get(Params.DESCRIPTION)));
    }
}
