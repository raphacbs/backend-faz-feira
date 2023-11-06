package com.coelho.fazfeira.behavior.enums;

import com.coelho.fazfeira.behavior.SearchBehavior;
import com.coelho.fazfeira.behavior.item.ItemSearchAll;
import com.coelho.fazfeira.behavior.item.ItemSearchByProductDesc;
import com.coelho.fazfeira.behavior.item.ItemSearchByProductDescIsAdded;
import com.coelho.fazfeira.behavior.item.ItemSearchIsAdded;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum EnumItemSearch {
    BY_PROD_DESCRIPTION(true, false, new ItemSearchByProductDesc()),
    BY_PROD_DESC_ADDED(true, true, new ItemSearchByProductDescIsAdded()),
    ALL_ADDED(false, true, new ItemSearchIsAdded()),
    ALL(false, false, new ItemSearchAll());

    private final boolean productDesParam;
    private final boolean isAddedParam;

    private final SearchBehavior searchBehavior;

    public static EnumItemSearch find(boolean productDesParam, boolean isAddedParam) {
        for (EnumItemSearch elem : values()) {
            if (elem.productDesParam == productDesParam &&
                    elem.isAddedParam() == isAddedParam) {
                return elem;
            }
        }
        return null;
    }

}
