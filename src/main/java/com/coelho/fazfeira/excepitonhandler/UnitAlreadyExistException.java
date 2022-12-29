package com.coelho.fazfeira.excepitonhandler;

public class UnitAlreadyExistException extends RuntimeException{

    public UnitAlreadyExistException(String message){
        super(message);
    }

    public UnitAlreadyExistException(String message, Throwable throwable){
        super(message, throwable);
    }

}
