package com.spynad.model.message;


import jakarta.xml.bind.annotation.XmlRootElement;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@XmlRootElement(name = "result")
public class Result implements Serializable {
    private static final long serialVersionUID = -11113967080513790L;
    private String result;
    private int status;
}
