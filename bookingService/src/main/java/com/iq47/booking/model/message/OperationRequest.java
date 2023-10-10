package com.iq47.booking.model.message;

import jakarta.xml.bind.annotation.XmlRootElement;
import lombok.AllArgsConstructor;
import lombok.Data;

@XmlRootElement(name = "Operation")
@AllArgsConstructor
@Data
public class OperationRequest {
    long operationId;
}
