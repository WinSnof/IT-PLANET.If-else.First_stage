package com.itplanet.contest.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = NotNullFieldsValidator.class)
public @interface NotNullFields {

    String message() default "List cannot contain elements of type : Null";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
