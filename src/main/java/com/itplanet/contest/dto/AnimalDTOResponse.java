package com.itplanet.contest.dto;

import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.Date;
import java.util.List;

public record AnimalDTOResponse(
        Long id,
        List<Long> animalTypes,
        Float weight,
        Float length,
        Float height,
        String gender,
        String lifeStatus,
        String chippingDateTime,
        Long chipperId,
        Long chippingLocationId,
        List<Long> visitedLocations,
        String deathDateTime
) {
}
