package com.coelho.fazfeira.behavior.enums;

import com.coelho.fazfeira.behavior.SearchBehavior;
import com.coelho.fazfeira.behavior.shoppinglist.ShoppingSearchByDescription;
import com.coelho.fazfeira.behavior.shoppinglist.ShoppingSearchByUser;
import com.coelho.fazfeira.behavior.shoppinglist.ShoppingSearchIsOpenAndDes;
import com.coelho.fazfeira.behavior.shoppinglist.ShoppingSearchIsOpenAndUser;
import com.coelho.fazfeira.behavior.supermarket.SupermarketSearchAPI;
import com.coelho.fazfeira.behavior.supermarket.SupermarketSearchByLonLat;
import com.coelho.fazfeira.behavior.supermarket.SupermarketSearchByName;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum EnumShoppingListSearch {
    BY_DESCRIPTION(true, true, false, new ShoppingSearchByDescription()),
    BY_IS_OPEN_DESCRIPTION(true, true, true,new ShoppingSearchIsOpenAndDes()),
    BY_IS_OPEN_USER(false, true,true, new ShoppingSearchIsOpenAndUser()),
    BY_USER(false, true, false, new ShoppingSearchByUser());

    private final boolean descriptionParam;
    private final boolean userIdParam;
    private final boolean isOpenParam;


    private final SearchBehavior searchBehavior;

    public static EnumShoppingListSearch find(boolean descriptionParam,
                                              boolean userIdParam,
                                              boolean isOpenParam) {
        for (EnumShoppingListSearch elem : values()) {
            if (elem.descriptionParam == descriptionParam &&
                    elem.userIdParam == userIdParam &&
                    elem.isOpenParam == isOpenParam) {
                return elem;
            }
        }
        return null;
    }

}
