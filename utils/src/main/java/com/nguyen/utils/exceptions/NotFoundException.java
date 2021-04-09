package com.nguyen.utils.exceptions;

public class NotFoundException extends RuntimeException{
    //Default no-args constructor
    public NotFoundException(){}
    public NotFoundException(String message){
        super(message);
    }
    public NotFoundException(Throwable thr){
        super(thr);
    }
    public NotFoundException(String message, Throwable thr){
        super(message,thr);
    }
}
