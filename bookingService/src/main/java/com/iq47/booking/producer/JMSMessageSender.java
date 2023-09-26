package com.iq47.booking.producer;

import com.iq47.booking.message.CancelBookingMessage;
import com.iq47.booking.message.MessageConverter;
import com.iq47.booking.message.SellTicketMessage;
import lombok.SneakyThrows;
import com.iq47.booking.model.entity.Operation;
import com.iq47.booking.model.entity.OperationStatus;
import com.iq47.booking.service.OperationalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.jms.*;

@Component

public class JMSMessageSender implements MessageSender {

    private final int TIMEOUT_SECONDS = 1200;

    private final int RESTART_SECONDS = 12000;

    private OperationalService asyncTaskService;

    private Queue queue;
    private ConnectionFactory connectionFactory;
    private MessageConverter messageConverter;

    private MessageProducer publisher;
    private Session session;

    @SneakyThrows
    @Autowired
    public JMSMessageSender(OperationalService asyncTaskService, Queue queue, ConnectionFactory connectionFactory, MessageConverter messageConverter) {
        this.asyncTaskService = asyncTaskService;
        this.queue = queue;
        this.connectionFactory = connectionFactory;
        this.messageConverter = messageConverter;
    }

    @PostConstruct
    void init() throws JMSException {
        Connection connection = connectionFactory.createConnection();
        this.session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        this.publisher = session.createProducer(queue);
        connection.start();
    }

    private Operation createOperation() {
        Operation operation = new Operation(null, null, null, TIMEOUT_SECONDS, RESTART_SECONDS, null, OperationStatus.PENDING);
        operation = this.asyncTaskService.save(operation);
        return operation;
    }

    @Override
    public Operation sendMessage(SellTicketMessage reportMessage) throws JMSException {
        Operation operation = createOperation();
        reportMessage.setTaskId(operation.getId());
        String text = messageConverter.convertMessage(reportMessage);
        return sendOperationalMessage(operation, text);
    }

    @Override
    public Operation sendMessage(CancelBookingMessage reportMessage) throws JMSException {
        Operation operation = createOperation();
        reportMessage.setTaskId(operation.getId());
        String text = messageConverter.convertMessage(reportMessage);
        return sendOperationalMessage(operation, text);
    }

    @Override
    public Operation sendOperationalMessage(Operation operation, String text) throws JMSException {
        operation.setMessage(text);
        this.asyncTaskService.save(operation);
        publisher.send(queue, session.createTextMessage(text));
        return operation;
    }
}
