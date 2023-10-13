package com.iq47.booking.model.message;

import jakarta.xml.bind.annotation.XmlRootElement;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@XmlRootElement(name = "Operation")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Operation {
    long id;
}
