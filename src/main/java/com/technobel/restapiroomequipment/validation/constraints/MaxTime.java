package com.technobel.restapiroomequipment.validation.constraints;

import com.technobel.restapiroomequipment.validation.validators.MaxTimeValidator;
import jakarta.validation.Constraint;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = MaxTimeValidator.class)
public @interface MaxTime {
    int h();
    int m();
    int s();
}
