package com.iq47.booking.model.message;


import javax.xml.bind.annotation.XmlRootElement;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@XmlRootElement(name = "operation")
public class OperationalStatusResponse {
    private Long id;
    private LocalDateTime time;
    private LocalDateTime finishedAt;
    private String status;
}