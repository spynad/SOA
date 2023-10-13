package com.spynad.firstservice.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "operation")
public class Operation {
    @XmlElement(name = "id")
    private Long id;


    public Long getOperationId() {
        return id;
    }

    public void setOperationId(Long id) {
        this.id = id;
    }
}
