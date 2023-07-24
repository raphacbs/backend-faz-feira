package com.coelho.fazfeira.handlers.actions.pricehistory;

import com.coelho.fazfeira.handlers.AbstractHandler;
import com.coelho.fazfeira.handlers.Context;
import com.coelho.fazfeira.model.Item;
import com.coelho.fazfeira.model.PriceHistory;
import com.coelho.fazfeira.model.ShoppingList;

import java.time.LocalDateTime;

import static com.coelho.fazfeira.constants.Constants.Context.*;

public class CreatePriceHistoryHandler extends AbstractHandler {
    @Override
    protected void doHandle(Context context) {

        Item item = context.getEntity(ITEM, Item.class);
        ShoppingList shoppingList = context.getEntity(SHOPPING_LIST, ShoppingList.class);

        final PriceHistory priceHistory = PriceHistory.builder()
                .createdAt(LocalDateTime.now())
                .product(item.getProduct())
                .shoppingList(shoppingList)
                .updatedAt(LocalDateTime.now())
                .supermarket(shoppingList.getSupermarket())
                .price(item.getPerUnit())
                .item(item)
                .build();

        context.setEntity(PRICE_HISTORY, priceHistory);
    }
}
