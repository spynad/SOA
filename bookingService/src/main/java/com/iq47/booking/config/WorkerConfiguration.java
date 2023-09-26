package com.iq47.booking.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile({"worker", "!web"})
@ComponentScan(basePackages = "com.iq47", includeFilters = {
        @ComponentScan.Filter(type = FilterType.REGEX, pattern="com.iq47.consumer.*")
}, useDefaultFilters = false)
public class WorkerConfiguration {
}
