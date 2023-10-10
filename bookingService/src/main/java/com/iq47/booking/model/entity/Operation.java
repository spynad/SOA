package com.iq47.booking.model.entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.Duration;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@Entity
@Table(name = "operation")
public class Operation {
    @Id
    @GeneratedValue
    private Long id;

    private LocalDateTime start;

    @Column(name = "finished_at")
    private LocalDateTime finishedAt;

    private OperationStatus status;

    public Operation() {
        this.start = LocalDateTime.now();
        this.status = OperationStatus.EXECUTION;
    }
}
