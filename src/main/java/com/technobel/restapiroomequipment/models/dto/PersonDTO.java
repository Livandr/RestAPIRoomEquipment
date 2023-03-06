package com.technobel.restapiroomequipment.models.dto;

import com.technobel.restapiroomequipment.models.entities.users.Person;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@Data
@Builder
public class PersonDTO implements Serializable {


    private final Long id;
    private final String role;
    private final String firstName;
    private final String lastName;

    private final String username;
    private final String email;
    private final String token;


    public static PersonDTO toDto(Person entity) {
        if( entity == null )
            return null;

        return new PersonDTO(

                entity.getId(),
                entity.getRole(),
                entity.getFirstname(),
                entity.getLastname(),
                entity.getUsername(),
                entity.getEmail(),
                builder().token
        );
    }
}
