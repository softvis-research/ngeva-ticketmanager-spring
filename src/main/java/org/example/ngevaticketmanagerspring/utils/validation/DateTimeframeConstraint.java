package org.example.ngevaticketmanagerspring.utils.validation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

@Constraint(validatedBy = DateTimeframeValidator.class)
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface DateTimeframeConstraint {
    String message() default "Birthday must be in between 01.01.1900 and today";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}