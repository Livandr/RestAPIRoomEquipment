package com.technobel.restapiroomequipment.services
        ;

import com.technobel.restapiroomequipment.models.dto.RoomDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface RoomService {

    List<RoomDTO> getAll();
    List<RoomDTO> findCompatibleRoomsForRequest(Long requestId);


}
