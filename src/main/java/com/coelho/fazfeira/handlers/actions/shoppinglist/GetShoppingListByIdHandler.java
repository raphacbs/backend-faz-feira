package com.coelho.fazfeira.handlers.actions.shoppinglist;

import com.coelho.fazfeira.excepitonhandler.BusinessException;
import com.coelho.fazfeira.handlers.AbstractHandler;
import com.coelho.fazfeira.handlers.Context;
import com.coelho.fazfeira.model.ShoppingList;
import com.coelho.fazfeira.model.User;
import com.coelho.fazfeira.repository.ShoppingListRepository;
import com.coelho.fazfeira.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

import static com.coelho.fazfeira.base.BusinessCode.SHOPPING_LIST_NOT_EXIST_FOR_USER;
import static com.coelho.fazfeira.constants.Constants.Context.SHOPPING_LIST;

@Component
public class GetShoppingListByIdHandler extends AbstractHandler {

    @Autowired
    private ShoppingListRepository shoppingListRepository;
    @Autowired
    private UserService userService;


    @Override
    protected void doHandle(Context context) {
        final ShoppingList shoppingList = context.getEntity(SHOPPING_LIST, ShoppingList.class);
        final UUID loggedUserId = userService.getLoggedUserId();

        ShoppingList shoppingListSaved = this.shoppingListRepository
                .findByIdAndUser(shoppingList.getId(), User.builder().id(loggedUserId).build()).orElseThrow(
                        () -> new BusinessException(SHOPPING_LIST_NOT_EXIST_FOR_USER)
                );

        context.setEntity(SHOPPING_LIST, shoppingListSaved);
    }
}
