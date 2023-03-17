package com.itplanet.contest.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.List;
import java.util.Objects;

public class NotNullFieldsValidator implements ConstraintValidator<NotNullFields, List<Long>> {

    @Override
    public void initialize(NotNullFields constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(List<Long> longs, ConstraintValidatorContext constraintValidatorContext) {
        return longs.stream().allMatch(Objects::nonNull);
    }
}
