package com.coelho.fazfeira.handlers.actions.product;

import com.coelho.fazfeira.handlers.AbstractHandler;
import com.coelho.fazfeira.handlers.Context;
import com.coelho.fazfeira.model.Product;
import com.coelho.fazfeira.model.ShoppingList;
import com.coelho.fazfeira.model.User;
import com.coelho.fazfeira.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

import static com.coelho.fazfeira.constants.Constants.Context.PRODUCT;
import static com.coelho.fazfeira.constants.Constants.Context.SHOPPING_LIST;

@Component
public class SaveProductHandler extends AbstractHandler {

    @Autowired
    private ProductRepository productRepository;

    @Override
    protected void doHandle(Context context) {
        final Product product = context.getEntity(PRODUCT, Product.class);
        product.setCreatedAt(LocalDateTime.now());
        product.setUpdateAt(LocalDateTime.now());
       Product saved =  this.productRepository.save(product);
       context.setEntity(PRODUCT, saved);
    }
}
