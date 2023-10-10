package com.iq47.booking.config;

import com.iq47.booking.model.entity.Operation;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import({Operation.class})
public class EntityConfig {
}
