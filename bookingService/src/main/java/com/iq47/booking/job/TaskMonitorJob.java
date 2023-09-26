package com.iq47.booking.job;

import com.iq47.booking.model.entity.Operation;
import com.iq47.booking.model.entity.OperationStatus;
import com.iq47.booking.producer.JMSMessageSender;
import com.iq47.booking.service.OperationalService;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Slf4j
@NoArgsConstructor
public class TaskMonitorJob implements Job {


    @Autowired
    OperationalService operationalService;

    @Autowired
    JMSMessageSender jmsMessageSender;

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        log.info("Job ** {} ** starting @ {}", jobExecutionContext.getJobDetail().getKey().getName(), jobExecutionContext.getFireTime());
        List<Operation> operationList = this.operationalService.getApplicableTimedOutOperations();
        for(Operation operation: operationList) {
            try {
                operation.setStatus(OperationStatus.PENDING);
                operationalService.save(operation);
                jmsMessageSender.sendOperationalMessage(operation, operation.getMessage());
                log.info("Task ** {} ** marked as ready for restart", operation.getId());
            } catch (Exception e) {
                e.printStackTrace();
                log.info("Error while restarting Task {}", operation.getId());
            }
        }

        List<Operation> timeoutOperationsList = this.operationalService.timeoutAllExpiredOperations();
        for(Operation operation: timeoutOperationsList) {
            log.info("Task ** {} ** timed out", operation.getId());
        }
        log.info("Job ** {} ** completed.  Next job scheduled @ {}", jobExecutionContext.getJobDetail().getKey().getName(), jobExecutionContext.getNextFireTime());
    }
}
