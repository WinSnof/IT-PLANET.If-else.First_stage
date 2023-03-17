package com.itplanet.contest.mapper;

import com.itplanet.contest.dto.LocationDTORequest;
import com.itplanet.contest.entity.Location;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
public class LocationDTORequestMapper implements Function<LocationDTORequest, Location> {
    @Override
    public Location apply(LocationDTORequest locationDTORequest) {
        return new Location(
                locationDTORequest.latitude(),
                locationDTORequest.longitude()
        );
    }
}
