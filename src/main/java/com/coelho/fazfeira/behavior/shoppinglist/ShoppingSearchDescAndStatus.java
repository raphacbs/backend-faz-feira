package com.coelho.fazfeira.behavior.shoppinglist;

import com.coelho.fazfeira.behavior.SearchBehavior;
import com.coelho.fazfeira.model.ShoppingList;
import com.coelho.fazfeira.model.ShoppingListStatus;
import com.coelho.fazfeira.model.User;
import com.coelho.fazfeira.repository.ShoppingListRepository;
import org.springframework.data.domain.Page;

import java.util.Map;
import java.util.UUID;

import static com.coelho.fazfeira.constants.Params.*;

public class ShoppingSearchDescAndStatus implements SearchBehavior<ShoppingList, ShoppingListRepository> {
    @Override
    public Page<ShoppingList> searchPage(ShoppingListRepository repository, Map<String, String> params) {
        UUID userId = UUID.fromString(params.get(USER_ID));
        String description = params.get(DESCRIPTION);
        ShoppingListStatus status = ShoppingListStatus.valueOf(params.get(SHOPPING_LIST_STATUS));

        final User user = User.builder().id(userId).build();
        return repository.findByUserAndDescriptionIgnoreCaseContainingAndStatus(getPageable(params),
                User.builder().id(userId).build(),
                description,
                status
                );
    }
}
