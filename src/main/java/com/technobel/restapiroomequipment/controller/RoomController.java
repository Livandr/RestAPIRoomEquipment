package com.technobel.restapiroomequipment.controller;

import com.technobel.restapiroomequipment.models.dto.RoomDTO;
import com.technobel.restapiroomequipment.services.EquipmentService;
import com.technobel.restapiroomequipment.services.RoomService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/room")
public class RoomController {
    private final RoomService roomService;
    private final EquipmentService equipmentService;

    public RoomController(RoomService roomService, EquipmentService equipmentService){
        this.roomService = roomService;
        this.equipmentService = equipmentService;
    }

    //Les utilisateurs connectés peuvent voir toutes les salles
    @GetMapping({"", "/all"})
    public List<RoomDTO> getAll(){

        return roomService.getAll();
    }


}
