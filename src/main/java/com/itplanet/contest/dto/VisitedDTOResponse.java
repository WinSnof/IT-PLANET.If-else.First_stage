package com.itplanet.contest.dto;

public record VisitedDTOResponse(
        Long id,
        String dateTimeOfVisitLocationPoint,
        Long locationPointId
) {
}
