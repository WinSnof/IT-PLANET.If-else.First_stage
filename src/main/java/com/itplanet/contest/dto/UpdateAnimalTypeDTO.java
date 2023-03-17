package com.itplanet.contest.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record UpdateAnimalTypeDTO(
        @NotNull
        @Min(1)
        Long oldTypeId,
        @NotNull
        @Min(1)
        Long newTypeId
) {
}
