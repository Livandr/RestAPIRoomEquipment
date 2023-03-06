package com.technobel.restapiroomequipment.services;

import com.technobel.restapiroomequipment.models.dto.ReducedRequestDTO;
import com.technobel.restapiroomequipment.models.dto.RequestDTO;
import com.technobel.restapiroomequipment.models.entities.RequestStatus;
import com.technobel.restapiroomequipment.models.forms.RequestForm;

import java.util.List;

public interface RequestService {

    List<RequestDTO> getAll();
    List<ReducedRequestDTO> getFutureWithStatus(RequestStatus status);
    RequestDTO getRequestDetails(Long id);

    public void create(RequestForm form);

    void cancelRequest(Long id);

    void acceptRequest(Long id, String message);
    void refuseRequest(Long id, String justification);
    void relocateRequest(Long id, String justification);


}
