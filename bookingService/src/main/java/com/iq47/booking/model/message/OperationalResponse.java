package com.iq47.booking.model.message;

import jakarta.xml.bind.annotation.XmlRootElement;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@XmlRootElement(name = "operation")
public class OperationalResponse {
    private Long id;
    private LocalDateTime time;
}
