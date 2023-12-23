package com.iq47.booking.model.data;

import javax.xml.bind.annotation.XmlRootElement;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
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
