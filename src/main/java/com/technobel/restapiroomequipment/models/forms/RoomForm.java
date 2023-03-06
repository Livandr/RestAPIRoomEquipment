package com.technobel.restapiroomequipment.models.forms;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class RoomForm {

    @NotBlank
    private String roomName;


}
