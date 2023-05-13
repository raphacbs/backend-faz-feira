package com.coelho.fazfeira.excepitonhandler;

import lombok.Getter;

@Getter
public class BusinessException extends RuntimeException{

    private String message;
    private Integer errorCode;

    public BusinessException(Integer errorCode){
        this.errorCode = errorCode;
    }

}
