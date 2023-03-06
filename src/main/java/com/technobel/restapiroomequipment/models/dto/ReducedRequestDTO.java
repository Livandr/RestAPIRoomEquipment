package com.technobel.restapiroomequipment.models.dto;

import com.technobel.restapiroomequipment.models.entities.Request;
import com.technobel.restapiroomequipment.models.entities.RequestStatus;
import com.technobel.restapiroomequipment.models.entities.Status;
import lombok.Data;
import org.apache.logging.log4j.util.PropertySource;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Comparator;

@Data
public class ReducedRequestDTO implements Serializable {

    private final Long id;
    private final LocalDate date;
    private final LocalTime beginTime;
    private final LocalTime endTime;
    private final int neededCapacity;
    private final RequestStatus currentStatus;
    private final String madeBy;
    private final String roomname;

    public static ReducedRequestDTO toDto(Request entity){

        if(entity == null)
            return null;

        return new ReducedRequestDTO(
                entity.getId(),
                entity.getDate(),
                entity.getBeginTime(),
                entity.getEndTime(),
                entity.getNeededCapacity(),
                entity.getStatusHistory().stream()
                        .max(Comparator.comparing(Status::getCreatedAt))
                        .map(Status::getStatus)
                        .orElse(null),
        entity.getMadeBy().getFirstname() + ' ' + entity.getMadeBy().getLastname().toUpperCase(),
                entity.getRoom() == null ? null : entity.getRoom().getRoomName()
        );
    }

}
