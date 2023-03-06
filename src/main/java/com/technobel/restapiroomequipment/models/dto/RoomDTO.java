package com.technobel.restapiroomequipment.models.dto;

import com.technobel.restapiroomequipment.models.entities.Room;
import lombok.Builder;
import lombok.Data;

import java.util.Set;

@Data@Builder
public class RoomDTO {

    private Long id;
    private String name;

    private int capacity;
    private boolean teacherOnly;
    private Set<RequestDTO> reservation;
    private Set<EquipmentDTO> equipments;


    public static RoomDTO toDto(Room room) {
        if(room == null)
            return null;

        return RoomDTO.builder()
                .id(room.getId())
                .name(room.getRoomName())
                .capacity(room.getCapacity())
                .teacherOnly(room.isTeacherOnly())
                .build();
    }
}
