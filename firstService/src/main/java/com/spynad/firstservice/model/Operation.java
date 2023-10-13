package com.spynad.firstservice.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "operation")
public class Operation {
    @XmlElement
    private Long id;

    @JsonProperty("operationId")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
