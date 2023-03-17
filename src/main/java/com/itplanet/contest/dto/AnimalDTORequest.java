package com.itplanet.contest.dto;

import com.itplanet.contest.entity.enumeration.Gender;
import com.itplanet.contest.validation.NotNullFields;
import com.itplanet.contest.validation.ValueOfEnum;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.hibernate.validator.constraints.UniqueElements;

import java.util.List;

public record AnimalDTORequest(
        @UniqueElements
        @NotNullFields
        @Size(min = 1)
        List<@Min(1) Long> animalTypes,
        @NotNull
        @DecimalMin(value = "0", inclusive = false)
        Float weight,
        @NotNull
        @DecimalMin(value = "0", inclusive = false)
        Float length,
        @NotNull
        @DecimalMin(value = "0", inclusive = false)
        Float height,
        @NotNull
        @ValueOfEnum(enumClazz = Gender.class)
        String gender,
        @NotNull
        @Min(1)
        Long chipperId,
        @NotNull
        @Min(1)
        Long chippingLocationId
) {
}
