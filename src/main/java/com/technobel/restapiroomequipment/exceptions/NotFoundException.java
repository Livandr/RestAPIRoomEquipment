package com.technobel.restapiroomequipment.exceptions;


import com.technobel.restapiroomequipment.models.entities.BaseEntity;
import lombok.Getter;

@Getter
public class NotFoundException extends RuntimeException{

    private final Class<? extends BaseEntity<?>> ressourceType;
    private final Object id;
    public NotFoundException(Class<? extends BaseEntity<?>> ressourceType, Object id){
        this(
                String.format("%s not found with %s", ressourceType.getSimpleName(),id.toString()),ressourceType, id
        );
    }



    protected NotFoundException(String message, Class<? extends BaseEntity<?>> ressourceType, Object id){
        super(message);
        this.ressourceType = ressourceType;
        this.id = id;
    }
}
