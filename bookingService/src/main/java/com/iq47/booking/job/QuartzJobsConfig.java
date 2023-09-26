package com.iq47.booking.job;

import com.iq47.booking.config.QuartzConfig;
import org.quartz.JobDetail;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.quartz.CronTriggerFactoryBean;
import org.springframework.scheduling.quartz.JobDetailFactoryBean;

@Configuration
public class QuartzJobsConfig {
    private static final String CRON_EVERY_MINUTE = "0 0/1 * ? * * *";

    @Bean(name = "taskMonitor")
    public JobDetailFactoryBean jobTaskMonitor() {
        return QuartzConfig.createJobDetail(TaskMonitorJob.class, "Task Monitor Job");
    }

    @Bean(name = "taskMonitorTrigger")
    public CronTriggerFactoryBean triggerTaskMonitor(@Qualifier("taskMonitor") JobDetail jobDetail) {
        return QuartzConfig.createCronTrigger(jobDetail, CRON_EVERY_MINUTE, "Task Monitor Trigger");
    }
}
