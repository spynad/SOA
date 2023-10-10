package com.iq47.booking.model.message;

import javax.xml.bind.annotation.XmlRootElement;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@XmlRootElement(name = "operation")
public class OperationalResponse {
    private Long id;
    private String time;
}
