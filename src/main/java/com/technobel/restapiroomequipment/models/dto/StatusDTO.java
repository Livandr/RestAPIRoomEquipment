package com.technobel.restapiroomequipment.models.dto;

import com.technobel.restapiroomequipment.models.entities.RequestStatus;
import com.technobel.restapiroomequipment.models.entities.Status;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class StatusDTO implements Serializable {

    private final LocalDateTime createdAt;
    private final Long id;
    private final RequestStatus status;
    private final String justification;

    public static StatusDTO toDto(Status status) {
        if(status == null)
            return null;

        return new StatusDTO(
                status.getCreatedAt(),
                status.getId(),
                status.getStatus(),
                status.getJustification()
        );
    }
}
