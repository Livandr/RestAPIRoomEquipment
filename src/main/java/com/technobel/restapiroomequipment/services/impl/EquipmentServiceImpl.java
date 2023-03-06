package com.technobel.restapiroomequipment.services.impl;

import com.technobel.restapiroomequipment.models.dto.EquipmentDTO;
import com.technobel.restapiroomequipment.repositories.EquipmentRepository;
import com.technobel.restapiroomequipment.services.EquipmentService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EquipmentServiceImpl implements EquipmentService {

    private final EquipmentRepository equipmentRepository;

    private EquipmentServiceImpl(EquipmentRepository equipmentRepository){
        this.equipmentRepository = equipmentRepository;
    }

    @Override
    public List<EquipmentDTO> getAll() {
        return equipmentRepository.findAll().stream()
                .map(EquipmentDTO :: toDto)
                .toList();
    }
}
