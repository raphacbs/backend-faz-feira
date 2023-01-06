package com.coelho.fazfeira.excepitonhandler;

public class EntityAlreadyExistException extends RuntimeException{

    public EntityAlreadyExistException(String message){
        super(message);
    }

    public EntityAlreadyExistException(String message, Throwable throwable){
        super(message, throwable);
    }

}
