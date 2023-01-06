package com.coelho.fazfeira.excepitonhandler;

public class EntityNotExistException extends RuntimeException{

    public EntityNotExistException(String message){
        super(message);
    }

    public EntityNotExistException(String message, Throwable throwable){
        super(message, throwable);
    }

}
