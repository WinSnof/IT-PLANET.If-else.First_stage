package com.itplanet.contest.dto;

import jakarta.validation.constraints.NotBlank;

public record TypeDTORequest(
        @NotBlank
        String type
) {
}
