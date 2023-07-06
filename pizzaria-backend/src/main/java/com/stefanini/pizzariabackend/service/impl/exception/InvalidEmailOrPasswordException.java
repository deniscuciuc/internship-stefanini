package com.stefanini.pizzariabackend.service.impl.exception;

import org.springframework.web.bind.annotation.ResponseStatus;

import static org.springframework.http.HttpStatus.FORBIDDEN;

@ResponseStatus(FORBIDDEN)
public class InvalidEmailOrPasswordException extends Exception {

    public InvalidEmailOrPasswordException(String message) {
        super(message);
    }
}
