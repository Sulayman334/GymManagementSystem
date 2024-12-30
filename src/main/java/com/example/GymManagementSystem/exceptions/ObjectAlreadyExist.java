package com.example.GymManagementSystem.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class ObjectAlreadyExist extends RuntimeException{
    public ObjectAlreadyExist(String message) {
        super(message);
    }
}
