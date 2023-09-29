package com.iq47.booking.model.data;

import javax.xml.bind.annotation.XmlRootElement;
import lombok.Data;

@Data
@XmlRootElement(name = "person")
public class Person {
    private Long id;
    private Double weight;
    private String eyeColor;
    private String hairColor;
    private String country;
}