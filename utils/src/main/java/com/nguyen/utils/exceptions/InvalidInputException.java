package com.nguyen.utils.exceptions;

public class InvalidInputException extends RuntimeException{
    //Default no-args constructor
    public InvalidInputException(){}
    public InvalidInputException(String message){
        super(message);
    }
    public InvalidInputException(Throwable thr){
        super(thr);
    }
    public InvalidInputException(String message,Throwable thr){
        super(message,thr);
    }
}
