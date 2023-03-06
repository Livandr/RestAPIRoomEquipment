package com.technobel.restapiroomequipment.models.dto;

import com.technobel.restapiroomequipment.models.entities.Equipment;
import lombok.Builder;
import lombok.Data;

@Data@Builder
public class EquipmentDTO {

    private long id;
    private String name;

    public static EquipmentDTO toDto(Equipment equipment){
        if (equipment == null)
            return null;

        return EquipmentDTO.builder()
                .id(equipment.getId())
                .name(equipment.getName())
                .build();
    }
}
