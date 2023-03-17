package com.itplanet.contest.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import jakarta.validation.ReportAsSingleViolation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = ValueOfEnumValidator.class)
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@ReportAsSingleViolation
public @interface ValueOfEnum {
    Class<? extends Enum<?>> enumClazz();

    String message() default "The value does not match any of the types";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
