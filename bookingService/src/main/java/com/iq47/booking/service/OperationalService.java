package com.iq47.booking.service;

import com.iq47.booking.model.entity.Operation;
import com.iq47.booking.model.entity.OperationStatus;
import com.iq47.booking.repository.OperationRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class OperationalService {

    OperationRepository repository;

    public OperationalService(OperationRepository repository) {
        this.repository = repository;
    }

    public Optional<Operation> getById(Long id) {
        return repository.findById(id);
    }

    public Operation save(Operation operation) {
        return repository.save(operation);
    }
}
