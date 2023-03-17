package com.itplanet.contest.dto;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;

public record LocationDTORequest(
        @NotNull
        @DecimalMin(value = "-90")
        @DecimalMax(value = "90")
        Double latitude,
        @NotNull
        @DecimalMin(value = "-180")
        @DecimalMax(value = "180")
        Double longitude
) {
}
