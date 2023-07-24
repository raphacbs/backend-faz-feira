package com.coelho.fazfeira.handlers.actions.product;

import com.coelho.fazfeira.handlers.AbstractHandler;
import com.coelho.fazfeira.handlers.Context;
import com.coelho.fazfeira.inputs.ItemWithPorductInput;
import com.coelho.fazfeira.inputs.ShoppingListInput;
import com.coelho.fazfeira.mapper.ProductMapper;
import com.coelho.fazfeira.model.Product;
import com.coelho.fazfeira.model.ShoppingList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.coelho.fazfeira.constants.Constants.Context.*;

//@Component
public class ConvertItemWithProductInputToEntityProductHandler extends AbstractHandler {

    private final Logger logger = LoggerFactory.getLogger(ConvertItemWithProductInputToEntityProductHandler.class);
    private final ProductMapper productMapper = ProductMapper.INSTANCE;



    @Override
    protected void doHandle(Context context) {
        final ItemWithPorductInput itemWithPorductInput = context.getInput(ITEM_INPUT, ItemWithPorductInput.class);
        logger.debug("Preparing object conversion");
        final Product product = this.productMapper.productInputToProduct(itemWithPorductInput.getProduct());
        context.setEntity(PRODUCT, product);
        logger.info("Object converted successfully");

    }
}
