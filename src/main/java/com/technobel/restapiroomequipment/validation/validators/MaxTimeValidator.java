package com.technobel.restapiroomequipment.validation.validators;

import com.technobel.restapiroomequipment.validation.constraints.MaxTime;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.Temporal;

public class MaxTimeValidator implements ConstraintValidator<MaxTime, Temporal> {

    MaxTime constraint;
    private LocalTime maxTime;

    @Override
    public void initialize(MaxTime constraintAnnotation) {
        constraint = constraintAnnotation;
        maxTime = LocalTime.of(
                getConstraint().h(),
                getConstraint().m(),
                getConstraint().s()
        );
    }

    @Override
    public boolean isValid(Temporal temporal, ConstraintValidatorContext context) {

        if(temporal == null)
            return true;

        LocalTime toValidate = toLocalTime(temporal);

        boolean valid = validate(toValidate);
        if( !valid )
            setupMessage(context, "should be max " + maxTime);

        return valid;

    }

    private boolean validate(LocalTime time){
        return time.isBefore(maxTime);
    }

    private LocalTime toLocalTime(Temporal temporal){

        if( temporal instanceof LocalTime )
            return  (LocalTime)temporal;
        else if (temporal instanceof LocalDateTime)
            return ((LocalDateTime) temporal).toLocalTime();
        else
            throw new IllegalArgumentException("value should be LocalTime or LocalDateTime");

    }

    protected void setupMessage(ConstraintValidatorContext context, String message){
        context.disableDefaultConstraintViolation();
        context.buildConstraintViolationWithTemplate(message)
                .addConstraintViolation();
    }

    public MaxTime getConstraint() {
        return constraint;
    }
}
