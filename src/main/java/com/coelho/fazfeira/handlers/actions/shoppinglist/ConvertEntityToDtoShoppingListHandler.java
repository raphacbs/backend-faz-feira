package com.coelho.fazfeira.handlers.actions.shoppinglist;

import com.coelho.fazfeira.dto.ShoppingListDto;
import com.coelho.fazfeira.handlers.AbstractHandler;
import com.coelho.fazfeira.handlers.Context;
import com.coelho.fazfeira.mapper.ShoppingListMapper;
import com.coelho.fazfeira.model.ShoppingList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.coelho.fazfeira.constants.Constants.Context.*;
//@Component
public class ConvertEntityToDtoShoppingListHandler extends AbstractHandler {

    private final Logger logger = LoggerFactory.getLogger(ConvertEntityToDtoShoppingListHandler.class);
    private final ShoppingListMapper shoppingListMapper = ShoppingListMapper.INSTANCE;



    @Override
    protected void doHandle(Context context) {
        final ShoppingList shoppingList = context.getEntity(SHOPPING_LIST, ShoppingList.class);
        final ShoppingListDto shoppingListDto = this.shoppingListMapper.shoppingListToShoppingListDto(shoppingList);
        context.setDto(SHOPPING_LIST_DTO, shoppingListDto);
        logger.info("Object converted successfully");
    }
}
