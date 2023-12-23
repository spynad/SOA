package com.spynad.exception;

import jakarta.xml.ws.WebFault;

@WebFault
public class NotFoundException extends ApiException {
    private int code;
    public NotFoundException (int code, String msg) {
        super(code, msg);
        this.code = code;
    }
}