package com.itplanet.contest.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record UpdateVisitedDTO(
        @NotNull
        @Min(1)
        Long visitedLocationPointId,
        @NotNull
        @Min(1)
        Long locationPointId
) {
}
