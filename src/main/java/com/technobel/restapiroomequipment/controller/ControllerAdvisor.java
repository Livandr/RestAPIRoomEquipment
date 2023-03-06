package com.technobel.restapiroomequipment.controller;

import com.technobel.restapiroomequipment.exceptions.NotFoundException;
import com.technobel.restapiroomequipment.models.dto.ErrorDTO;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.client.ResourceAccessException;

import java.time.LocalDateTime;

@ControllerAdvice
public class ControllerAdvisor {

    @ResponseBody
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(ResourceAccessException.class)
    public ErrorDTO handleResourceNotFound(NotFoundException ex, HttpServletRequest req){

        return ErrorDTO.builder()
                .status(HttpStatus.NOT_FOUND)
                .message(ex.getMessage())
                .requestMadeAt(LocalDateTime.now())
                .URI(req.getRequestURI())
                .build();
    }

    HttpHeaders headers = new HttpHeaders();
}
