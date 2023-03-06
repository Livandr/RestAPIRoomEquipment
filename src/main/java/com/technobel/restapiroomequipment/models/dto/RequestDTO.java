package com.technobel.restapiroomequipment.models.dto;

import com.technobel.restapiroomequipment.models.entities.Request;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Set;

@Data @Builder
public class RequestDTO implements Serializable {

    private final Long id;
    private final LocalDateTime beginAt;
    private final LocalDateTime endAt;
    private final int neededCapacity;
    private final String justification;
    private final String additionalNotes;
    private final List<StatusDTO> statusHistory;
    private final String currentStatus;
    private final PersonDTO madeBy;
    private final RoomDTO room;

    public static RequestDTO toDto(Request entity) {
        if(entity == null)
            return null;

        List<StatusDTO> statusHistory = entity.getStatusHistory().stream()
                .map(StatusDTO::toDto)
                .sorted(Comparator.comparing(StatusDTO::getCreatedAt).reversed())
                .toList();

        String currentStatus = statusHistory.stream()
                .findFirst()
                .map(status -> status.getStatus().name())
                .orElse(null);

        return new RequestDTO(
                entity.getId(),
                entity.getDate().atTime(entity.getBeginTime()),
                entity.getDate().atTime(entity.getEndTime()),
                entity.getNeededCapacity(),
                entity.getJustification(),
                entity.getAdditionalNotes(),
                statusHistory,
                currentStatus,
                PersonDTO.toDto(entity.getMadeBy()),
                RoomDTO.toDto(entity.getRoom())
        );
    }
}
