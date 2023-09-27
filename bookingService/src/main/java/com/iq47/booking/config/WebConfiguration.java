package com.iq47.booking.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile({"web", "!worker"})
@ComponentScan(basePackages = "com.iq47.booking", includeFilters = {
        @ComponentScan.Filter(type = FilterType.REGEX, pattern="com.iq47.booking.controller.*"),
        @ComponentScan.Filter(type = FilterType.REGEX, pattern="com.iq47.booking.job.*")
}, useDefaultFilters = false)
public class WebConfiguration {
}
