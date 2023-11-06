package com.coelho.fazfeira.handlers.actions.shoppinglist;

import com.coelho.fazfeira.handlers.AbstractHandler;
import com.coelho.fazfeira.handlers.Context;
import com.coelho.fazfeira.handlers.actions.supermarket.GetSupermarketByIdHandler;
import com.coelho.fazfeira.inputs.ShoppingListInput;
import com.coelho.fazfeira.mapper.ShoppingListMapper;
import com.coelho.fazfeira.model.ShoppingList;
import com.coelho.fazfeira.model.Supermarket;
import com.coelho.fazfeira.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.UUID;

import static com.coelho.fazfeira.constants.Constants.Context.*;

@Component
public class UpdateShoppingListFromInputHandler extends AbstractHandler {

    private final ShoppingListMapper shoppingListMapper = ShoppingListMapper.INSTANCE;

    @Autowired
    private UserService userService;



    @Override
    protected void doHandle(Context context) {
        final ShoppingListInput shoppingListInput = context.getInput(SHOPPING_LIST_INPUT, ShoppingListInput.class);
        final ShoppingList shoppingList = context.getEntity(SHOPPING_LIST, ShoppingList.class);
        context.setEntity(SUPERMARKET,
                Supermarket.builder().id(UUID.fromString(shoppingListInput.getSupermarketId())).build());
        callHandler(GetSupermarketByIdHandler.class, context );
        shoppingListMapper.updateShoppingListFromShoppingListInput(shoppingListInput,
                shoppingList, context.getEntity(SUPERMARKET, Supermarket.class));
    }
}
