package com.technobel.restapiroomequipment.exceptions;

public class RequestStatusException extends RuntimeException{

    public RequestStatusException(){
        super("can't change status this way");
    }
}
