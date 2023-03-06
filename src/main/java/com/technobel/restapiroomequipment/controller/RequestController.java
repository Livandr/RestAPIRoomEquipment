package com.technobel.restapiroomequipment.controller;

import com.technobel.restapiroomequipment.models.dto.ReducedRequestDTO;
import com.technobel.restapiroomequipment.models.entities.RequestStatus;
import com.technobel.restapiroomequipment.models.forms.RequestForm;
import com.technobel.restapiroomequipment.services.EquipmentService;
import com.technobel.restapiroomequipment.services.RequestService;
import com.technobel.restapiroomequipment.services.RoomService;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/request")
public class RequestController {

    private final RequestService requestService;
    private final RoomService roomService;
    private final EquipmentService equipmentService;

    public RequestController(RequestService requestService, RoomService roomService, EquipmentService equipmentService){
        this.requestService = requestService;
        this.roomService = roomService;
        this.equipmentService = equipmentService;
    }

    //Les admins peuvent voir toutes les demande de réservation en cours
    //GET - http://localhost:8080/request
    @GetMapping({
            "",
            "/all"
    })
    public List<ReducedRequestDTO> getFutureWithStatus(RequestStatus status, Authentication auth){
        String username = (String) auth.getPrincipal();
        return requestService.getFutureWithStatus(status);
    }

    //on récupère une réservation avec son id
    //GET - http://localhost:8080/request/{id}
    @GetMapping("/{id:[0-9]+}")
    public RequestService getRequestDetails(@PathVariable ("id") long id){
        return (RequestService) requestService.getRequestDetails(id);

    }

    //Les étudiants et les professeurs peuvent effectuer des réservations
    //GET - http://localhost:8080/request/new
    @PostMapping("/new")
    @ResponseStatus(HttpStatus.CREATED)
    public void create(@RequestBody RequestForm form){
        requestService.create(form);
    }



// Request Status
//    @GetMapping({
//            "/{status:PENDING}",
//            "/{status:REFUSED}",
//            "/{status:ACCEPTED}",
//            "/{status:RELOCATING}"
//    })
    //Les admins acceptent, refusent ou relocalisent les requests
//    @PutMapping("/{id:[0-9]+}/update")
//    public void update(@PathVariable long id, @RequestBody RequestForm form){
//        switch (form.getStatusChange()) {
//            case RequestStatus.ACCEPTED -> requestService.acceptRequest(id, "request accepted");
//            case RequestStatus.REFUSED -> requestService.refuseRequest(id, "refused");
//            case RequestStatus.RELOCATING -> requestService.relocatedRequest(id, "relocating");
//        }
//    }

    @DeleteMapping("{id:[0-9]+}/cancel")
    public void cancelRequest(@PathVariable long id){
        requestService.cancelRequest(id);
    }

    @PatchMapping("/{id:[0-9]+}/{status:ACCEPTED}")
    public void acceptRequest(@PathVariable long id, @PathVariable String status){
        requestService.acceptRequest(id, "accepted");
    }

    @PutMapping("/{id:[0-9]+}/{status:REFUSED}")
    public void refuseRequest(@PathVariable long id, @PathVariable String status){
        requestService.refuseRequest(id, "not available/room not for student");
    }

    @PutMapping("/{id:[0-9]+}/{status:RELOCATING}")
    public void relocateRequest(@PathVariable long id, @PathVariable String status){
       requestService.relocateRequest(id, "relocating");
    }





}
