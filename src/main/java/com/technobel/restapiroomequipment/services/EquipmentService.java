package com.technobel.restapiroomequipment.services;

import com.technobel.restapiroomequipment.models.dto.EquipmentDTO;

import java.util.List;

public interface EquipmentService {

    List<EquipmentDTO> getAll();
}
