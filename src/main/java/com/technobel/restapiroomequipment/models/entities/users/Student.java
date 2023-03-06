package com.technobel.restapiroomequipment.models.entities.users;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("STUDENT")
public class Student extends Person{

    @Override
    public void prePersist() {
        super.prePersist();
        this.setRole("STUDENT");
    }
}
