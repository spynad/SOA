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
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "operation")
public class Operation {
    @Id
    @GeneratedValue
    private Long id;

    private LocalDateTime start;

    private String message;

    @Column(name = "timeout")
    private int timeoutSeconds;

    @Column(name = "restart_period")
    private int restartPeriodSeconds;

    @Column(name = "finished_at")
    private LocalDateTime finishedAt;

    private OperationStatus status;
}
