package com.example.GymManagementSystem.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class NullDetails extends RuntimeException{
    public NullDetails(String message) {
        super(message);
    }
}
