package com.coelho.fazfeira.handlers.actions.item;

import com.coelho.fazfeira.handlers.AbstractHandler;
import com.coelho.fazfeira.handlers.Context;
import com.coelho.fazfeira.model.Item;
import com.coelho.fazfeira.model.ShoppingList;
import com.coelho.fazfeira.model.User;
import com.coelho.fazfeira.repository.ItemRepository;
import com.coelho.fazfeira.repository.ShoppingListRepository;
import com.coelho.fazfeira.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

import static com.coelho.fazfeira.constants.Constants.Context.ITEM;
import static com.coelho.fazfeira.constants.Constants.Context.SHOPPING_LIST;

@Component
public class SaveItemHandler extends AbstractHandler {

    @Autowired
    private ItemRepository itemRepository;
    @Autowired
    private UserService userService;



    @Override
    protected void doHandle(Context context) {
        final Item itemToSaved = context.getEntity(ITEM, Item.class);
        if (itemToSaved.getId() == null) {
            itemToSaved.setCreatedAt(LocalDateTime.now());
        }
        itemToSaved.setUpdatedAt(LocalDateTime.now());
        this.itemRepository.save(itemToSaved);
    }
}
