package com.itplanet.contest.service;

import com.itplanet.contest.dto.UpdateVisitedDTO;
import com.itplanet.contest.dto.VisitedDTOResponse;
import com.itplanet.contest.specification.VisitedSearchSpec;

import java.util.List;

public interface IVisitedService {
    List<VisitedDTOResponse> search(VisitedSearchSpec specification, Integer from, Integer size, Long animalId);

    VisitedDTOResponse addVisitedPoint(Long animalId, Long pointId);

    VisitedDTOResponse updateAnimalVisited(UpdateVisitedDTO updateVisitedDTO, Long id);

    void deleteAnimalVisited(Long animalId, Long visitedPointId);
}
