package com.iq47.booking.model.data;

import javax.xml.bind.annotation.XmlRootElement;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@XmlRootElement(name = "coordinates")
@NoArgsConstructor
@XmlAccessorType(XmlAccessType.FIELD)
public class Coordinates {
    private Long id;
    private Double x;
    private Double y;
}
