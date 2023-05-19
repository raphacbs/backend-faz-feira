package com.coelho.fazfeira.handlers.actions.shoppinglist;

import com.coelho.fazfeira.handlers.AbstractHandler;
import com.coelho.fazfeira.handlers.Context;
import com.coelho.fazfeira.model.ShoppingList;
import com.coelho.fazfeira.model.User;
import com.coelho.fazfeira.repository.ShoppingListRepository;
import com.coelho.fazfeira.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

import static com.coelho.fazfeira.constants.Constants.Context.SHOPPING_LIST;

@Component
public class SaveShoppingListHandler extends AbstractHandler {

    @Autowired
    private ShoppingListRepository shoppingListRepository;
    @Autowired
    private UserService userService;



    @Override
    protected void doHandle(Context context) {
        final ShoppingList shoppingList = context.getEntity(SHOPPING_LIST, ShoppingList.class);
        if (shoppingList.getId() == null) {
            shoppingList.setCreatedAt(LocalDateTime.now());
            shoppingList.setUser(User.builder().id(userService.getLoggedUserId()).build());
        }
        shoppingList.setUpdatedAt(LocalDateTime.now());
        this.shoppingListRepository.save(shoppingList);
    }
}
