package com.technobel.restapiroomequipment.validation.validators;

import com.technobel.restapiroomequipment.validation.constraints.MinTime;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.Temporal;

public class MinTimeValidator implements ConstraintValidator<MinTime, Temporal> {

    private MinTime constraint;
    private LocalTime minTime;

    @Override
    public void initialize(MinTime constraintAnnotation) {

        constraint = constraintAnnotation;

        minTime = LocalTime.of(
                getConstraint().h(),
                getConstraint().m(),
                getConstraint().s()
        );
    }

    @Override
    public boolean isValid(Temporal temporal, ConstraintValidatorContext context) {
        if(temporal == null)
            return true;

        LocalTime toValidate = toLocaltime(temporal);

        boolean valid = validate (toValidate);

        if(!valid)
            setupMessage(context, "should be min " + minTime);

        return valid;
    }

    private boolean validate (LocalTime time){
        return time.isAfter(minTime);
    }

    private LocalTime toLocaltime(Temporal temporal){

        if(temporal instanceof LocalTime)
            return (LocalTime) temporal;
        else if (temporal instanceof LocalDateTime)
            return ((LocalDateTime) temporal).toLocalTime();
        else
            throw new IllegalArgumentException("temporal should be LocalTime or LocalDateTime");
    }

    protected void setupMessage(ConstraintValidatorContext context, String message){
        context.disableDefaultConstraintViolation();
        context.buildConstraintViolationWithTemplate(message)
                .addConstraintViolation();
    }

    public MinTime getConstraint() {
        return constraint;
    }
}
