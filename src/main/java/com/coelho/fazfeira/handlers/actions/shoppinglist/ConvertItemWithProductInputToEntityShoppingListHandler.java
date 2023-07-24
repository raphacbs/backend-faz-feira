package com.coelho.fazfeira.handlers.actions.shoppinglist;

import com.coelho.fazfeira.handlers.AbstractHandler;
import com.coelho.fazfeira.handlers.Context;
import com.coelho.fazfeira.inputs.ItemWithPorductInput;
import com.coelho.fazfeira.mapper.ShoppingListMapper;
import com.coelho.fazfeira.model.Product;
import com.coelho.fazfeira.model.ShoppingList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.coelho.fazfeira.constants.Constants.Context.*;

//@Component
public class ConvertItemWithProductInputToEntityShoppingListHandler extends AbstractHandler {

    private final Logger logger = LoggerFactory.getLogger(ConvertItemWithProductInputToEntityShoppingListHandler.class);
    private final ShoppingListMapper shoppingListMapper = ShoppingListMapper.INSTANCE;



    @Override
    protected void doHandle(Context context) {
        final ItemWithPorductInput itemWithPorductInput = context.getInput(ITEM_INPUT, ItemWithPorductInput.class);
        logger.debug("Preparing object conversion");
        final ShoppingList shoppingList = shoppingListMapper.shoppingListDtoToShoppingList(itemWithPorductInput.getShoppingList());
        context.setEntity(SHOPPING_LIST, shoppingList );
        logger.info("Object converted successfully");

    }
}
