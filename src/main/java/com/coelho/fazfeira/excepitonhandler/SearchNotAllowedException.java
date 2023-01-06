package com.coelho.fazfeira.excepitonhandler;

public class SearchNotAllowedException extends RuntimeException{
    public SearchNotAllowedException(String message){
        super(message);
    }
    public SearchNotAllowedException(Throwable e){
        super(e);
    }



    public SearchNotAllowedException(String message, Throwable throwable){
        super(message, throwable);
    }
}
