package com.iq47.booking.model.data;

import javax.xml.bind.annotation.XmlRootElement;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@XmlRootElement(name = "person")
@NoArgsConstructor
@XmlAccessorType(XmlAccessType.FIELD)
public class Person {
    private Long id;
    private Double weight;
    private String eyeColor;
    private String hairColor;
    private String country;
}
