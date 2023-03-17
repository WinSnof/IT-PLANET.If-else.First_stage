package com.itplanet.contest.dto;

public record AccountDTOResponse(
        Long id,
        String firstName,
        String lastName,
        String email
) {
}
