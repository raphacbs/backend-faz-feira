package com.coelho.fazfeira.excepitonhandler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice(basePackages = {"com.coelho.fazfeira.controller", "com.coelho.fazfeira.filter"})
public class UnitControllerAdvice {

    @ResponseBody
    @ExceptionHandler(UnitAlreadyExistException.class)
    public ResponseEntity<MessageExceptionHandler> unitAlreadyExist(UnitAlreadyExistException unitAlreadyExistException){
        MessageExceptionHandler error = new MessageExceptionHandler(HttpStatus.CONFLICT.value(), unitAlreadyExistException.getMessage());
        return new ResponseEntity<>(error, HttpStatus.CONFLICT);
    }

    @ResponseBody
    @ExceptionHandler(UnknownException.class)
    public ResponseEntity<MessageExceptionHandler> unknown(UnknownException unknownException){
        MessageExceptionHandler error = new MessageExceptionHandler(HttpStatus.INTERNAL_SERVER_ERROR.value(),  "unknown error");
        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ResponseBody
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<MessageExceptionHandler> any(ResourceValidationException resourceValidationException){
        MessageExceptionHandler error = new MessageExceptionHandler(HttpStatus.BAD_REQUEST.value(), resourceValidationException.getMessage());
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ResponseBody
    @ExceptionHandler(UnitNotExistException.class)
    public ResponseEntity<MessageExceptionHandler> unitNotExist(UnitNotExistException unitNotExistException){
        MessageExceptionHandler error = new MessageExceptionHandler(HttpStatus.NO_CONTENT.value(), unitNotExistException.getMessage());
        return new ResponseEntity<>(error, HttpStatus.NO_CONTENT);
    }
    @ResponseBody
    @ExceptionHandler(TokenException.class)
    public ResponseEntity<MessageExceptionHandler> tokenNotExist(TokenException tokenException){
        MessageExceptionHandler error = new MessageExceptionHandler(HttpStatus.UNAUTHORIZED.value(),  tokenException.getMessage());
        return new ResponseEntity<>(error, HttpStatus.UNAUTHORIZED);
    }

}
