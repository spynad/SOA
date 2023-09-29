package com.spynad.firstservice.model.message;

import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlTransient;

@XmlRootElement(name = "error")
public class ApiResponseMessage {

    int code;
    String message;

    public ApiResponseMessage(){}

    public ApiResponseMessage(String message){
        this.code = code;
        this.message = message;
    }

    @XmlTransient
    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
