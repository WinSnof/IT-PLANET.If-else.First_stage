package com.itplanet.contest.service;

import com.itplanet.contest.dto.LocationDTORequest;
import com.itplanet.contest.dto.LocationDTOResponse;

public interface ILocationService {
    LocationDTOResponse findById(Long id);

    LocationDTOResponse save(LocationDTORequest locationDTOResponse);

    LocationDTOResponse update(LocationDTORequest locationDTORequest, Long pointId);
    void delete(Long pointId);
}
