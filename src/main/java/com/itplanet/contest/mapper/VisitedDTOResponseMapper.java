package com.itplanet.contest.mapper;

import com.itplanet.contest.dto.VisitedDTOResponse;
import com.itplanet.contest.entity.Visited;
import org.springframework.stereotype.Service;

import java.time.format.DateTimeFormatter;
import java.util.function.Function;

@Service
public class VisitedDTOResponseMapper implements Function<Visited, VisitedDTOResponse> {

    private static final DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSXXX");

    @Override
    public VisitedDTOResponse apply(Visited visited) {
        return new VisitedDTOResponse(
                visited.getId(),
                visited.getDateTimeOfVisitLocationPoint().format(dtf),
                visited.getLocation().getId()
        );
    }
}
