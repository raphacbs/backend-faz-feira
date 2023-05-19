package com.coelho.fazfeira.handlers.actions.shoppinglist;

import com.coelho.fazfeira.handlers.AbstractHandler;
import com.coelho.fazfeira.handlers.Context;
import com.coelho.fazfeira.inputs.ShoppingListInput;
import com.coelho.fazfeira.mapper.ShoppingListMapper;
import com.coelho.fazfeira.model.ShoppingList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.coelho.fazfeira.constants.Constants.Context.SHOPPING_LIST;
import static com.coelho.fazfeira.constants.Constants.Context.SHOPPING_LIST_INPUT;
//@Component
public class ConvertInputToEntityShoppingListHandler extends AbstractHandler {

    private final Logger logger = LoggerFactory.getLogger(ConvertInputToEntityShoppingListHandler.class);
    private final ShoppingListMapper shoppingListMapper = ShoppingListMapper.INSTANCE;



    @Override
    protected void doHandle(Context context) {
        final ShoppingListInput shoppingListInput = context.getInput(SHOPPING_LIST_INPUT, ShoppingListInput.class);
        logger.debug("Preparing object conversion UnitRequestBody to Unit. {}",
                shoppingListInput);
        ShoppingList shoppingList =  this.shoppingListMapper.shoppingListRequestToShoppingList(shoppingListInput);
        context.setEntity(SHOPPING_LIST, shoppingList);
        logger.info("Object converted successfully");

    }
}
