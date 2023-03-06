package com.technobel.restapiroomequipment.repositories;

import com.technobel.restapiroomequipment.models.entities.Equipment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EquipmentRepository extends JpaRepository<Equipment, Long> {
}
