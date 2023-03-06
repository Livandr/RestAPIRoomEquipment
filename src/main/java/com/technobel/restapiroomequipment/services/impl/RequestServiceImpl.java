package com.technobel.restapiroomequipment.services.impl;

import com.technobel.restapiroomequipment.exceptions.RequestStatusException;
import com.technobel.restapiroomequipment.models.dto.ReducedRequestDTO;
import com.technobel.restapiroomequipment.models.dto.RequestDTO;
import com.technobel.restapiroomequipment.models.entities.Request;
import com.technobel.restapiroomequipment.models.entities.RequestStatus;
import com.technobel.restapiroomequipment.models.entities.Status;
import com.technobel.restapiroomequipment.models.entities.users.Person;
import com.technobel.restapiroomequipment.models.forms.RequestForm;
import com.technobel.restapiroomequipment.repositories.EquipmentRepository;
import com.technobel.restapiroomequipment.repositories.PersonRepository;
import com.technobel.restapiroomequipment.repositories.RequestRepository;
import com.technobel.restapiroomequipment.services.RequestService;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Objects;

@Service
public class RequestServiceImpl implements RequestService {
    private final RequestRepository requestRepository;
    private final EquipmentRepository equipmentRepository;
    private final PersonRepository personRepository;

    public RequestServiceImpl(RequestRepository requestRepository, EquipmentRepository equipmentRepository, PersonRepository personRepository){
        this.requestRepository = requestRepository;
        this.equipmentRepository = equipmentRepository;
        this.personRepository = personRepository;
    }
    @Override
    public void create(RequestForm form){
        Request request = form.toEntity();

        //user
        Person p = personRepository.findByUsername(form.getUsername())
                .orElseThrow();

        if(Objects.equals(p.getRole(), "ADMIN"))
            throw new RuntimeException();

        request.setMadeBy(p);

        //equipments
        request.setEquipments(
                new LinkedHashSet<>(equipmentRepository.findAllById(form.getEquipmentIds()))
        );

        //SENT status
        Status status = new Status();
        status.setRequest(request);
        status.setJustification("REQUEST_SENT");
        status.setStatus(RequestStatus.PENDING);

        request.getStatusHistory().add(status);

        requestRepository.save(request);
    }

    @Override
    public void cancelRequest(Long id) {

    }



    @Override
    public List<RequestDTO> getAll() {
        return requestRepository.findAll().stream()
                .map(RequestDTO::toDto)
                .toList();
    }

    @Override
    public List<ReducedRequestDTO> getFutureWithStatus(RequestStatus status) {
        return requestRepository.findFutureWithCurrentStatus(status).stream()
                .map(ReducedRequestDTO::toDto)
                .toList();
    }

    @Override
    public RequestDTO getRequestDetails(Long id){
        return requestRepository.findById(id)
                .map(RequestDTO :: toDto)
                .orElseThrow();
    }

    @Override
    public void acceptRequest(Long id, String message) {

    }

    @Override
    public void refuseRequest(Long id, String justification) {
            Request request = requestRepository.findById(id)
                .orElseThrow();

            Status currentStatus = request.getStatusHistory().stream()
                .max(Comparator.comparing(Status::getCreatedAt))
                .orElse(null);

            if(
                currentStatus != null &&
                    currentStatus.getStatus() != RequestStatus.PENDING &&
                    currentStatus.getStatus() != RequestStatus.RELOCATING
            )
                throw new RequestStatusException();

            Status status = new Status();
            status.setJustification(justification != null ? justification : "NO JUSTIFICATION");
            status.setRequest(request);
            status.setStatus(RequestStatus.REFUSED);

            request.getStatusHistory().add(status);
            requestRepository.save(request);
        }

        @Override
        public void relocateRequest(Long id, String justification) {
            Request request = requestRepository.findById(id)
                    .orElseThrow();

            Status currentStatus = request.getStatusHistory().stream()
                    .max(Comparator.comparing(Status::getCreatedAt))
                    .orElse(null);

            if( currentStatus != null && currentStatus.getStatus() != RequestStatus.ACCEPTED )
                throw new RequestStatusException();

            Status status = new Status();
            status.setJustification(justification != null ? justification : "no justification");
            status.setRequest(request);
            status.setStatus(RequestStatus.RELOCATING);

            request.getStatusHistory().add(status);
            requestRepository.save(request);
        }
}




