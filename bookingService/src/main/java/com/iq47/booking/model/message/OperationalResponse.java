package com.iq47.booking.model.message;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class OperationalResponse {
    private Long id;
    private LocalDateTime time;
}
