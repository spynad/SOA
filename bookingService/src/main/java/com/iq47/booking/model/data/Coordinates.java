package com.iq47.booking.model.data;

import javax.xml.bind.annotation.XmlRootElement;
import lombok.Data;

@Data
@XmlRootElement(name = "coordinates")
public class Coordinates {
    private Long id;
    private Double x;
    private Double y;
}
