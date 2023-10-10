package com.iq47.booking.model.message;


import javax.xml.bind.annotation.XmlRootElement;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@XmlRootElement(name = "operation")
public class OperationalStatusResponse {
    private Long id;
    private String time;
    private String finishedAt;
    private String status;
}