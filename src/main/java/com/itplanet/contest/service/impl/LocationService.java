package com.itplanet.contest.service.impl;

import com.itplanet.contest.dto.LocationDTORequest;
import com.itplanet.contest.dto.LocationDTOResponse;
import com.itplanet.contest.entity.Location;
import com.itplanet.contest.exception.BadRequestEx;
import com.itplanet.contest.exception.ConflictEx;
import com.itplanet.contest.exception.NotFoundEx;
import com.itplanet.contest.mapper.LocationDTORequestMapper;
import com.itplanet.contest.mapper.LocationDTOResponseMapper;
import com.itplanet.contest.repository.AnimalRepo;
import com.itplanet.contest.repository.LocationRepo;
import com.itplanet.contest.repository.VisitedRepo;
import com.itplanet.contest.service.ILocationService;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class LocationService implements ILocationService {

    private final LocationRepo locationRepo;
    private final VisitedRepo visitedRepo;
    private final AnimalRepo animalRepo;
    private final LocationDTOResponseMapper locationDTOResponseMapper;
    private final LocationDTORequestMapper locationDTORequestMapper;
    private static final String LATITUDE_AND_LONGITUDE_ALREADY_EXISTS = "Location point with already existing values. Latitude : [%f], Longitude : [%f]";

    @Override
    public LocationDTOResponse findById(Long id) {
        return locationDTOResponseMapper.apply(locationRepo
                .findById(id)
                .orElseThrow(() -> new NotFoundEx(id)));
    }

    @Override
    public LocationDTOResponse save(LocationDTORequest locationDTOResponse) {
        if(isLocationPointAlreadyExist(locationDTOResponse))
            throw new ConflictEx(String
                .format(LATITUDE_AND_LONGITUDE_ALREADY_EXISTS,
                        locationDTOResponse.latitude(),
                        locationDTOResponse.latitude()));
        return locationDTOResponseMapper.apply(locationRepo.save(locationDTORequestMapper.apply(locationDTOResponse)));
    }

    @Override
    public LocationDTOResponse update(LocationDTORequest locationDTOResponse, Long pointId) {
        if(isLocationPointAlreadyExist(locationDTOResponse))
            throw new ConflictEx(String
                .format(LATITUDE_AND_LONGITUDE_ALREADY_EXISTS,
                        locationDTOResponse.latitude(),
                        locationDTOResponse.latitude()));
        locationRepo
                .findById(pointId)
                .orElseThrow(() -> new NotFoundEx(pointId));
        Location toUpdate = locationDTORequestMapper.apply(locationDTOResponse);
        toUpdate.setId(pointId);
        return locationDTOResponseMapper.apply(locationRepo.save(toUpdate));
    }

    @Override
    public void delete(Long pointId) {
        locationRepo
                .findById(pointId)
                .orElseThrow(() -> new NotFoundEx(pointId));
        if(!visitedRepo.findByLocationId(pointId).isEmpty())
            throw new BadRequestEx();
        if(!animalRepo.findByChippingLocationIdId(pointId).isEmpty())
            throw new BadRequestEx();
        locationRepo.deleteById(pointId);
    }

    private boolean isLocationPointAlreadyExist(LocationDTORequest locationDTORequest) {
        return locationRepo.findByLatitudeAndLongitude(locationDTORequest.latitude(), locationDTORequest.longitude()).isPresent();
    }
}
