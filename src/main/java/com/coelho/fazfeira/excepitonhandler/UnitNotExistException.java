package com.coelho.fazfeira.excepitonhandler;

public class UnitNotExistException extends RuntimeException{

    public UnitNotExistException(String message){
        super(message);
    }

    public UnitNotExistException(String message, Throwable throwable){
        super(message, throwable);
    }

}
