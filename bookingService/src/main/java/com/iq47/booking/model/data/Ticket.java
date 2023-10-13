package com.iq47.booking.model.data;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.joda.time.LocalDateTime;


@Data
@AllArgsConstructor
@NoArgsConstructor
@XmlAccessorType(XmlAccessType.FIELD)
public class Ticket {

    private Long id;

    private String name;

    private Coordinates coordinates;

//    @XmlJavaTypeAdapter(XmlLocalDateTimeAdapter.class)
    private LocalDateTime creationDate;

    private Integer price;

    private Integer discount;

    private Boolean refundable;

    private String type;

    private Person person;
}
