package com.coelho.fazfeira.excepitonhandler;

public class ShoppingListStatusException extends RuntimeException{

    public ShoppingListStatusException(String message){
        super(message);
    }

    public ShoppingListStatusException(String message, Throwable throwable){
        super(message, throwable);
    }

}
