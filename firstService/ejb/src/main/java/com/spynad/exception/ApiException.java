package com.spynad.exception;


import jakarta.xml.ws.WebFault;

@WebFault
public class ApiException extends Exception{
    private int code;
    public ApiException (int code, String msg) {
        super(msg);
        this.code = code;
    }
}
