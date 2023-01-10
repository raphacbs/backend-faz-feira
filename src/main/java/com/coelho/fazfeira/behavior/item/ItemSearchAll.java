package com.coelho.fazfeira.behavior.item;

import com.coelho.fazfeira.behavior.SearchBehavior;
import com.coelho.fazfeira.constants.Params;
import com.coelho.fazfeira.model.Item;
import com.coelho.fazfeira.model.ShoppingList;
import com.coelho.fazfeira.repository.ItemRepository;
import org.springframework.data.domain.Page;

import java.util.Map;
import java.util.UUID;

public class ItemSearchAll implements SearchBehavior<Item, ItemRepository> {
    @Override
    public Page<Item> searchPage(ItemRepository repository, Map<String, String> params) {
        UUID id = UUID.fromString(params.get(Params.ITEM_SHOPPING_LIST_ID));
        return repository.findByShoppingList(getPageable(params),
                ShoppingList.builder().id(id).build());
    }
}
