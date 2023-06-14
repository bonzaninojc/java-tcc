package com.ifsc.julio.javatcc.exception;

public class StationException extends Exception {

    public StationException(String msg){
        super(msg);
    }

    public StationException(String msg, Throwable cause){
        super(msg, cause);
    }
}
