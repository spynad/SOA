package com.spynad.model;

import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlTransient;

import java.io.Serializable;

@XmlRootElement(name = "operation")
public class Operation implements Serializable {
    private static final long serialVersionUID = -55967080513790L;
    @XmlElement(name = "id")
    private Long id;

    @XmlTransient
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
