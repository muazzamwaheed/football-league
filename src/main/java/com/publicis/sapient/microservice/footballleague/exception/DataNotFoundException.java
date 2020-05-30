package com.publicis.sapient.microservice.footballleague.exception;

public class DataNotFoundException extends RuntimeException{

    public DataNotFoundException(String message) {
        super(message);
    }

}
