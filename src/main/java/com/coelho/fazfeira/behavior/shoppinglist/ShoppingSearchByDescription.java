package com.coelho.fazfeira.behavior.shoppinglist;

import com.coelho.fazfeira.behavior.SearchBehavior;
import com.coelho.fazfeira.model.ShoppingList;
import com.coelho.fazfeira.model.User;
import com.coelho.fazfeira.repository.ShoppingListRepository;
import org.springframework.data.domain.Page;

import java.util.Map;
import java.util.UUID;

import static com.coelho.fazfeira.constants.Params.DESCRIPTION;
import static com.coelho.fazfeira.constants.Params.USER_ID;

public class ShoppingSearchByDescription implements SearchBehavior<ShoppingList, ShoppingListRepository> {
    @Override
    public Page<ShoppingList> searchPage(ShoppingListRepository repository, Map<String, String> params) {
        UUID userId = UUID.fromString(params.get(USER_ID));
        String description = params.get(DESCRIPTION);
        final User user = User.builder().id(userId).build();
        return repository.findByUserAndDescriptionIgnoreCaseContaining(getPageable(params), user, description);
    }
}
