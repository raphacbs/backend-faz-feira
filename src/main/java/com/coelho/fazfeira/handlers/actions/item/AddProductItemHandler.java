package com.coelho.fazfeira.handlers.actions.item;

import com.coelho.fazfeira.handlers.AbstractHandler;
import com.coelho.fazfeira.handlers.Context;
import com.coelho.fazfeira.model.Item;
import com.coelho.fazfeira.model.Product;

import static com.coelho.fazfeira.constants.Constants.Context.ITEM;
import static com.coelho.fazfeira.constants.Constants.Context.PRODUCT;


public class AddProductItemHandler extends AbstractHandler {
    @Override
    protected void doHandle(Context context) {
        final Item itemToSaved = context.getEntity(ITEM, Item.class);
        final Product product = context.getEntity(PRODUCT, Product.class);
        itemToSaved.setProduct(product);
    }
}
