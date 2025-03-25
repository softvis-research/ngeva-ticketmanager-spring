package org.example.ngevaticketmanagerspring.utils.validation;

import java.time.LocalDate;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class DateTimeframeValidator implements ConstraintValidator<DateTimeframeConstraint, LocalDate> {

    @Override
    public void initialize(DateTimeframeConstraint contactNumber) {
    }

    public boolean isValid(LocalDate birthday, ConstraintValidatorContext cxt) {
        return !(birthday.isBefore(LocalDate.of(1900, 1, 1)) || birthday.isAfter(LocalDate.now()));
    }
}