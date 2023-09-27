package com.iq47.booking.consumer;

import com.iq47.booking.model.entity.Operation;
import com.iq47.booking.model.entity.OperationStatus;
import com.iq47.booking.model.exception.CancelOperationException;
import com.iq47.booking.model.exception.TimeOutException;
import com.iq47.booking.service.BookingService;
import com.iq47.booking.service.OperationalService;
import lombok.SneakyThrows;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.jms.*;
import javax.transaction.Transactional;
import java.time.LocalDateTime;

@Component
public class JMSMessageListener implements MessageListener {

    private ConnectionFactory connectionFactory;

    private OperationalService asyncTaskService;

    private final BookingService bookingService;

    private Queue queue;


    @Autowired
    public JMSMessageListener(ConnectionFactory connectionFactory, OperationalService asyncTaskService, BookingService bookingService, Queue queue) throws JMSException {
        this.asyncTaskService = asyncTaskService;
        this.bookingService = bookingService;
        this.queue = queue;
        this.connectionFactory = connectionFactory;
    }

    @SneakyThrows
    @PostConstruct
    void init() {
        Connection connection = connectionFactory.createConnection();
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        MessageConsumer consumer = session.createConsumer(queue);
        consumer.setMessageListener(this);
        connection.start();
    }

    @Override
    public void onMessage(Message m) {
        Operation task = null;
        try {
            TextMessage msg = (TextMessage) m;
            System.out.println("following message is received:" + msg.getText());
            JSONObject jo = new JSONObject(msg.getText());
            Long taskId = jo.getLong("task_id");
            task = asyncTaskService.getById(taskId).get();
            task.setStart(LocalDateTime.now());
            task.setStatus(OperationStatus.EXECUTION);
            asyncTaskService.save(task);
            String type = jo.getString("type");
            switch (type) {
                case "sell_ticket": {
                    handleSellTicketMessage(jo, task);
                    break;
                }
                case "cancel_booking": {
                    handleCancelBookingMessage(jo, task);
                    break;
                }
                default:
                    throw new RuntimeException("Unknown task type received.");
            }
        } catch (Exception e) {
            if(task != null)
                task.setStatus(OperationStatus.ERROR);
            throw new RuntimeException(e);
        } finally {
            if(task != null && !task.getStatus().equals(OperationStatus.CANCELLED)) {
                task.setFinishedAt(LocalDateTime.now());
                asyncTaskService.save(task);
            }
        }
    }

    private void handleCancelBookingMessage(JSONObject jo, Operation task) {
        bookingService.sellTicket(jo.getLong("ticket_id"), jo.getLong("person_id"), jo.getInt("price"), jo.getLong("task_id"));
        task.setStatus(OperationStatus.FINISHED);
    }

    private void handleSellTicketMessage(JSONObject jo, Operation task) throws TimeOutException {
        bookingService.cancelBooking(jo.getLong("person_id"), jo.getLong("task_id"));
        task.setStatus(OperationStatus.FINISHED);
    }

    @Transactional
    public void finishReport(Operation task) {
        try {
            if(!task.getStatus().equals(OperationStatus.TIMED_OUT)) {
                task.setStatus(OperationStatus.FINISHED);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
