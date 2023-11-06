package com.coelho.fazfeira.handlers.actions.product;

import com.coelho.fazfeira.excepitonhandler.BusinessException;
import com.coelho.fazfeira.handlers.AbstractHandler;
import com.coelho.fazfeira.handlers.Context;
import com.coelho.fazfeira.handlers.actions.shoppinglist.ConvertInputToEntityShoppingListHandler;
import com.coelho.fazfeira.model.Product;
import com.coelho.fazfeira.repository.ProductRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static com.coelho.fazfeira.base.BusinessCode.ProductCode.PRODUCT_ALREADY_EXIST;
import static com.coelho.fazfeira.constants.Constants.Context.PRODUCT;

@Component
public class ValidateProductAlreadyExistByCodeHandler extends AbstractHandler {


    private final Logger logger = LoggerFactory.getLogger(ValidateProductAlreadyExistByCodeHandler.class);

    @Autowired
    private ProductRepository productRepository;
    @Override
    protected void doHandle(Context context) {
        final Product product = context.getEntity(PRODUCT, Product.class);
        this.productRepository.findByCode(product.getCode()).ifPresent(existingProduct -> {
            throw new BusinessException(PRODUCT_ALREADY_EXIST);
        });
    }
}
