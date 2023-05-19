package com.coelho.fazfeira.base;

public interface BusinessCode {
     static  Integer ITEM = 1000000;
    static  Integer SHOPPING_LIST = 2000000;
    static  Integer SUPERMARKET = 3000000;
    public static  Integer SHOPPING_LIST_NOT_EXIST_FOR_USER = SHOPPING_LIST + 10;
    public static  Integer SHOPPING_LIST_STATUS_NOT_ADD_ITEMS = SHOPPING_LIST + 20;

    interface  SupermarketCode {
        public static  Integer SUPERMARKET_NOT_EXIST = SUPERMARKET + 10;

    }
}
