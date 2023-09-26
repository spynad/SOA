package com.iq47.booking.repository;

import com.iq47.booking.model.entity.Operation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OperationRepository extends JpaRepository<Operation, Long> {

    @Query(value = "SELECT * FROM task WHERE status=3 AND finished_at + restart_period * interval '1 second' < NOW()", nativeQuery = true)
    List<Operation> getApplicableTimedOutOperations();

    @Query(value = "SELECT * FROM task WHERE status=1 AND start + timeout * interval '1 second' < NOW()", nativeQuery = true)
    List<Operation> getAboutToTimeOutOperations();

}
