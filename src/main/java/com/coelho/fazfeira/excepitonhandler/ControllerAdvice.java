package com.coelho.fazfeira.excepitonhandler;

import com.coelho.fazfeira.ErrorCode;
import com.coelho.fazfeira.handlers.actions.shoppinglist.ConvertEntityToDtoShoppingListHandler;
import com.coelho.fazfeira.repository.ErrorCodeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.WebRequest;

import java.util.Optional;

@org.springframework.web.bind.annotation.ControllerAdvice(basePackages = {"com.coelho.fazfeira.controller",
        "com.coelho.fazfeira.filter"})
public class ControllerAdvice {
    @Autowired
    private ErrorCodeRepository errorCodeRepository;

    private final Logger logger = LoggerFactory.getLogger(ControllerAdvice.class);

    @ResponseBody
    @ExceptionHandler(UnitAlreadyExistException.class)
    public ResponseEntity<MessageExceptionHandler> unitAlreadyExist(UnitAlreadyExistException unitAlreadyExistException) {
        MessageExceptionHandler error = new MessageExceptionHandler(HttpStatus.CONFLICT.value(),
                unitAlreadyExistException.getMessage());
        return new ResponseEntity<>(error, HttpStatus.CONFLICT);
    }

    @ResponseBody
    @ExceptionHandler(EntityAlreadyExistException.class)
    public ResponseEntity<MessageExceptionHandler> entityAlreadyExist(EntityAlreadyExistException entityAlreadyExistException) {
        MessageExceptionHandler error = new MessageExceptionHandler(HttpStatus.CONFLICT.value(),
                entityAlreadyExistException.getMessage());
        return new ResponseEntity<>(error, HttpStatus.CONFLICT);
    }

    @ResponseBody
    @ExceptionHandler(UnknownException.class)
    public ResponseEntity<MessageExceptionHandler> unknown(UnknownException unknownException) {
        MessageExceptionHandler error = new MessageExceptionHandler(HttpStatus.INTERNAL_SERVER_ERROR.value(),
                "unknown error");
        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ResponseBody
    @ExceptionHandler(Exception.class)
    public ResponseEntity<MessageExceptionHandler> handleException(Exception ex, WebRequest request) {
        MessageExceptionHandler errorResponse = new MessageExceptionHandler(HttpStatus.INTERNAL_SERVER_ERROR.value(),
                "unknown error");
        logger.error("Error: ",ex);
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ResponseBody
    @ExceptionHandler(EntityNotExistException.class)
    public ResponseEntity<MessageExceptionHandler> unitNotExist(EntityNotExistException entityNotExistException) {
        MessageExceptionHandler error = new MessageExceptionHandler(HttpStatus.NO_CONTENT.value(),
                entityNotExistException.getMessage());
        return new ResponseEntity<>(error, HttpStatus.NO_CONTENT);
    }

    @ResponseBody
    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<MessageExceptionHandler> entityNotFound(NotFoundException notFoundException) {
        MessageExceptionHandler error = new MessageExceptionHandler(HttpStatus.BAD_REQUEST.value(),
                notFoundException.getMessage());
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ResponseBody
    @ExceptionHandler(TokenException.class)
    public ResponseEntity<MessageExceptionHandler> tokenNotExist(TokenException tokenException) {
        MessageExceptionHandler error = new MessageExceptionHandler(HttpStatus.UNAUTHORIZED.value(),
                tokenException.getMessage());
        return new ResponseEntity<>(error, HttpStatus.UNAUTHORIZED);
    }

    @ResponseBody
    @ExceptionHandler(SearchNotAllowedException.class)
    public ResponseEntity<MessageExceptionHandler> searchNotAllowed(SearchNotAllowedException searchNotAllowedException) {
        MessageExceptionHandler error = new MessageExceptionHandler(HttpStatus.BAD_REQUEST.value(),
                searchNotAllowedException.getMessage());
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ResponseBody
    @ExceptionHandler(ShoppingListStatusException.class)
    public ResponseEntity<MessageExceptionHandler> shoopingListStatus(ShoppingListStatusException shoppingListStatusException) {
        MessageExceptionHandler error = new MessageExceptionHandler(HttpStatus.CONFLICT.value(),
                shoppingListStatusException.getMessage());
        return new ResponseEntity<>(error, HttpStatus.CONFLICT);
    }

    @ResponseBody
    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<MessageExceptionHandler> business(BusinessException businessException) {
        try {
            final Optional<ErrorCode> errorCodeOptional = this.errorCodeRepository
                    .findById(businessException.getErrorCode());
            if(errorCodeOptional.isEmpty()){
                MessageExceptionHandler error = new MessageExceptionHandler(businessException.getErrorCode(),
                        "There is something wrong with your request.");
                return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
            }else{
                MessageExceptionHandler error = new MessageExceptionHandler(businessException.getErrorCode(),
                        errorCodeOptional.get().getMessage());
                return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
            }
        } catch (Exception e) {
            MessageExceptionHandler error = new MessageExceptionHandler(businessException.getErrorCode(),
                    "There is something wrong with your request.");
            return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
        }

    }

}
