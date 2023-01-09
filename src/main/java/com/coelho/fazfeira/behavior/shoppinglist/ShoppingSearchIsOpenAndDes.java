package com.coelho.fazfeira.behavior.shoppinglist;

import com.coelho.fazfeira.behavior.SearchBehavior;
import com.coelho.fazfeira.model.ShoppingList;
import com.coelho.fazfeira.model.User;
import com.coelho.fazfeira.repository.ShoppingListRepository;
import org.springframework.data.domain.Page;

import java.util.Map;
import java.util.UUID;

import static com.coelho.fazfeira.constants.Params.*;

public class ShoppingSearchIsOpenAndDes implements SearchBehavior<ShoppingList, ShoppingListRepository> {
    @Override
    public Page<ShoppingList> searchPage(ShoppingListRepository repository, Map<String, Object> params) {
        UUID userId = (UUID) params.get(USER_ID);
        String description = String.valueOf(params.get(DESCRIPTION));
        boolean isOpen = Boolean.parseBoolean(params.get(SHOPPING_LIST_IS_OPEN).toString());
        final User user = User.builder().id(userId).build();
        return repository.findByUserAndDescriptionIgnoreCaseContainingAndIsOpen(getPageable(params),
                user,
                description,
                isOpen
                );
    }
}
