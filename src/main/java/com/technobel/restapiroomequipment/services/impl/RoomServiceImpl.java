package com.technobel.restapiroomequipment.services.impl;

import com.technobel.restapiroomequipment.exceptions.NotFoundException;
import com.technobel.restapiroomequipment.models.dto.RoomDTO;
import com.technobel.restapiroomequipment.models.entities.Request;
import com.technobel.restapiroomequipment.models.entities.Room;
import com.technobel.restapiroomequipment.repositories.RequestRepository;
import com.technobel.restapiroomequipment.repositories.RoomRepository;
import com.technobel.restapiroomequipment.services.RoomService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoomServiceImpl implements RoomService {

    private final RoomRepository roomRepository;
    private final RequestRepository requestRepository;

    public RoomServiceImpl(RoomRepository roomRepository, RequestRepository requestRepository){
        this.roomRepository = roomRepository;
        this.requestRepository = requestRepository;
    }

    @Override
    public List<RoomDTO> getAll() {
        return roomRepository.findAll().stream()
                .map(RoomDTO :: toDto)
                .toList();
    }


    //Liste des salles correspondantes à la résa
    public List<RoomDTO> findCompatibleRoomsForRequest (Long requestId){

        Request request = requestRepository.findById(requestId)
                .orElseThrow( //*() -> new NotFoundException(Request.class, requestId)
                );

        String madeByRole = request.getMadeBy().getRole();

        List<Room> potentialRooms = roomRepository.searchRoomForTeacher(
                request.getNeededCapacity(),
                request.getDate(),
                request.getBeginTime(),
                request.getEndTime()
        );

        return potentialRooms.stream()
                .filter(
                        room -> room.getEquipments()
                                .containsAll(request.getEquipments())
                )
                .map(RoomDTO :: toDto)
                .toList();
    }


}
