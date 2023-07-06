package com.stefanini.pizzariabackend.service.impl.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

@ResponseStatus(BAD_REQUEST)
public class InvalidPageValueException extends RuntimeException{

    public InvalidPageValueException(String message) {
        super(message);
    }

    public HttpStatus getResponseStatus() {
        return BAD_REQUEST;
    }
}
