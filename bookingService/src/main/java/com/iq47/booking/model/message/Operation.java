package com.iq47.booking.model.message;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "Operation")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Operation {
    long id;
}
