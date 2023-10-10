package com.iq47.booking.service;

import com.iq47.booking.model.entity.Operation;
import com.iq47.booking.model.entity.OperationStatus;
import com.iq47.booking.model.exception.UnableToCancelException;
import com.iq47.booking.model.message.OperationalResponse;
import com.iq47.booking.model.message.OperationalStatusResponse;
import com.iq47.booking.repository.OperationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.NoSuchElementException;
import java.util.concurrent.*;

@Service
public class BookingOperationalServiceImpl implements BookingOperationalService {

    private final BookingService bookingService;

    private final ExecutorService executorService = Executors.newFixedThreadPool(10);

    private final OperationRepository operationRepository;

    private final OperationalService operationalService;



    @Autowired
    public BookingOperationalServiceImpl(BookingService bookingService, OperationRepository operationRepository, OperationalService operationalService) {
        this.bookingService = bookingService;
        this.operationRepository = operationRepository;
        this.operationalService = operationalService;
    }


    @Override
    public OperationalResponse cancelBooking(Long personId) {
        Operation operation = new Operation();
        operation = operationRepository.save(operation);
        Operation finalOperation = operation;
        executorService.execute(() -> bookingService.cancelBooking(personId, finalOperation.getId()));
        return new OperationalResponse(operation.getId(), operation.getStart());
    }

    @Override
    public OperationalStatusResponse getOperationStatus(Long id) {
        Operation operation = operationalService.getById(id).orElseThrow(NoSuchElementException::new);
        return new OperationalStatusResponse(id, operation.getStart(), operation.getFinishedAt(), operation.getStatus().toString());
    }

    @Override
    public OperationalStatusResponse cancelOperation(Long id) throws UnableToCancelException {
        Operation operation = cancelOperationInternal(id);
        return new OperationalStatusResponse(id, operation.getStart(), operation.getFinishedAt(), operation.getStatus().toString());
    }

    @Transactional
    public Operation cancelOperationInternal(Long id) throws UnableToCancelException {
        Operation operation = operationalService.getById(id).orElseThrow(NoSuchElementException::new);
        if (operation.getStatus().equals(OperationStatus.EXECUTION)) {
            operation.setStatus(OperationStatus.REQUESTED_CANCELLATION);
            operationalService.save(operation);
            return operation;
        } else {
            throw new UnableToCancelException();
        }
    }

    @Override
    public OperationalResponse sellTicket(Long ticketId, Long personId, Integer price) {
        Operation operation = new Operation();
        operation = operationRepository.save(operation);
        Operation finalOperation = operation;
        executorService.execute(() -> bookingService.sellTicket(ticketId, personId, price, finalOperation.getId()));
        return new OperationalResponse(operation.getId(), operation.getStart());
    }
}
