package com.technobel.restapiroomequipment.repositories;

import com.technobel.restapiroomequipment.models.entities.Request;
import com.technobel.restapiroomequipment.models.entities.RequestStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface RequestRepository extends JpaRepository<Request, Long> {

    @Query("""
        SELECT r
        FROM Request r
        WHERE r.date >= now() AND (
            SELECT s.status
            FROM Status s
            WHERE r.id = s.request.id
            ORDER BY s.createdAt DESC
            LIMIT 1
        ) = :status
        """)
    List<Request> findFutureWithCurrentStatus(RequestStatus status);
}
