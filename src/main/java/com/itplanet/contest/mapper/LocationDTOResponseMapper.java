package com.itplanet.contest.mapper;

import com.itplanet.contest.dto.LocationDTOResponse;
import com.itplanet.contest.entity.Location;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
public class LocationDTOResponseMapper implements Function<Location, LocationDTOResponse> {
    @Override
    public LocationDTOResponse apply(Location location) {
        return new LocationDTOResponse(
                location.getId(),
                location.getLatitude(),
                location.getLongitude()
        );
    }
}
