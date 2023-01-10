package com.coelho.fazfeira.behavior.enums;

import com.coelho.fazfeira.behavior.SearchBehavior;
import com.coelho.fazfeira.behavior.shoppinglist.ShoppingSearchByDescription;
import com.coelho.fazfeira.behavior.shoppinglist.ShoppingSearchByUser;
import com.coelho.fazfeira.behavior.shoppinglist.ShoppingSearchDescAndStatus;
import com.coelho.fazfeira.behavior.shoppinglist.ShoppingSearchUserAndStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum EnumShoppingListSearch {
    BY_DESCRIPTION(true, true, false, new ShoppingSearchByDescription()),
    BY_DESCRIPTION_STATUS(true, true, true, new ShoppingSearchDescAndStatus()),
    BY_STATUS_STATUS(false, true, true, new ShoppingSearchUserAndStatus()),
    BY_USER(false, true, false, new ShoppingSearchByUser());

    private final boolean descriptionParam;
    private final boolean userIdParam;
    private final boolean statusParam;


    private final SearchBehavior searchBehavior;

    public static EnumShoppingListSearch find(boolean descriptionParam,
                                              boolean userIdParam,
                                              boolean statusParam) {
        for (EnumShoppingListSearch elem : values()) {
            if (elem.descriptionParam == descriptionParam &&
                    elem.userIdParam == userIdParam &&
                    elem.statusParam == statusParam) {
                return elem;
            }
        }
        return null;
    }

}
