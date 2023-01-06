package com.coelho.fazfeira.behavior.product;

import com.coelho.fazfeira.behavior.SearchBehavior;
import com.coelho.fazfeira.excepitonhandler.SearchNotAllowedException;
import com.coelho.fazfeira.model.Product;
import com.coelho.fazfeira.repository.ProductRepository;
import org.springframework.data.domain.Page;

import java.util.Map;

public class ProductNoSearch implements SearchBehavior<Product, ProductRepository> {
    @Override
    public Page<Product> searchPage(ProductRepository repository, Map<String, Object> params) {
        throw new SearchNotAllowedException("Enter at least one of the description or code parameters in the request");
    }
}
