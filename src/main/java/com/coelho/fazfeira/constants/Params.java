package com.coelho.fazfeira.constants;

import java.util.HashMap;
import java.util.Map;

public class Params {
    private Params() {
        throw new IllegalStateException("Utility class");
    }

    public static final String SUPERMARKET_NAME = "name";
    public static final String SHOPPING_LIST_SUPERMARKET_ID = "supermarketId";
    public static final String SUPERMARKET_RADIUS = "radiusM";
    public static final String SUPERMARKET_LONGITUDE = "longitude";
    public static final String SUPERMARKET_LATITUDE = "latitude";
    public static final String PRODUCT_CODE = "code";
    public static final String UNIT_DESCRIPTION = "description";
    public static final String UNIT_INITIALS = "initials";
    public static final String DESCRIPTION = "description";
    public static final String PARAM_PRODUCT_DESC = "productDesc";
    public static final String PARAM_PRODUCT_CODE = "productCode";
    public static final String ITEM_UPDATED_AT = "updatedAt";
    public static final String ITEM_PRICE = "price";
    public static final String ITEM_IS_ADDED = "isAdded";
    public static final String ITEM_SHOPPING_LIST_ID = "shoppingListId";
    public static final String SHOPPING_LIST_STATUS = "status";
    public static final String ID = "id";
    public static final String USER_ID = "userId";
    public static final String NO_PAGE = "pageNo";
    public static final String PAGE_SIZE = "pageSize";
    public static final String SORT_BY = "sortBy";
    public static final String SORT_DIR = "sortDir";

    public static Map<String, String> getDefaultParams() {
        Map<String, String> params = new HashMap<>();
        params.put(PAGE_SIZE, "10");
        params.put(NO_PAGE, "1");
        params.put(SORT_BY, "description");
        params.put(SORT_DIR, "asc");
        return params;
    }
}
