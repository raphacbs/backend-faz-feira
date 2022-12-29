package com.coelho.fazfeira.excepitonhandler;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
public class MessageExceptionHandler {
    private Integer status;
    private String message;
}
