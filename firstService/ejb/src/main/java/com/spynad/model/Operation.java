package com.spynad.model;

import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

import java.io.Serializable;

@XmlRootElement(name = "operation")
public class Operation implements Serializable {
    private static final long serialVersionUID = -55967080513790L;
    @XmlElement(name = "id")
    private Long id;


    public Long getOperationId() {
        return id;
    }

    public void setOperationId(Long id) {
        this.id = id;
    }
}
