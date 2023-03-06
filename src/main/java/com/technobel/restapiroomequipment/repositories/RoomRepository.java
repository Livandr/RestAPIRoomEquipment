package com.technobel.restapiroomequipment.repositories;

import com.technobel.restapiroomequipment.models.entities.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public interface RoomRepository extends JpaRepository<Room, Long> {

    @Query("""
        SELECT room
        FROM Room room
            JOIN Request req ON room.id = req.room.id
            JOIN Status status ON status.request.id = req.id
        WHERE 
            room.capacity >= :capacity AND (
            req.date != :begin_date OR
            (req.beginTime > :end_time AND req.endTime < :begin_time)
            )
""")
    List<Room> searchRoomForTeacher(
            int capacity,
            LocalDate begin_date,
            LocalTime begin_time,
            LocalTime end_time
    );
}
