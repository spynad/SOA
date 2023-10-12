package com.spynad.firstservice.model;

import jakarta.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "operation")
public class Operation {
    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
