package com.itplanet.contest.service.impl;

import com.itplanet.contest.dto.UpdateVisitedDTO;
import com.itplanet.contest.dto.VisitedDTOResponse;
import com.itplanet.contest.entity.Animal;
import com.itplanet.contest.entity.Location;
import com.itplanet.contest.entity.Visited;
import com.itplanet.contest.entity.enumeration.LifeStatus;
import com.itplanet.contest.exception.BadRequestEx;
import com.itplanet.contest.exception.NotFoundEx;
import com.itplanet.contest.mapper.VisitedDTOResponseMapper;
import com.itplanet.contest.repository.AnimalRepo;
import com.itplanet.contest.repository.LocationRepo;
import com.itplanet.contest.repository.VisitedRepo;
import com.itplanet.contest.service.IVisitedService;
import com.itplanet.contest.specification.VisitedSearchSpec;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class VisitedService implements IVisitedService {

    private final VisitedRepo visitedRepo;
    private final AnimalRepo animalRepo;
    private final LocationRepo locationRepo;
    private final VisitedDTOResponseMapper visitedDTOResponseMapper;
    private static final String ATTEMPT_TO_ADD_POINT_ANIMAL_DEAD_MSG =
            "Attempting to add a location to an animal with LifeStatus.DEAD. Animal ID : [%d]";
    private static final String ATTEMPT_TO_ADD_FIRST_POINT_EQUALS_CHIPPING_LOCATION =
            "Attempt to add the first point equal to the chipping point of the animal. Point ID : [%d]";
    private static final String ATTEMPT_TO_ADD_POINT_EQUAL_TO_THE_CURRENT_ONE =
            "Attempt to add a location point equal to the current one. Point ID : [%d]";
    private static final String ATTEMPT_TO_UPDATE_POINT_MATCHES_PREVIOUS_OR_NEXT =
            "Update the location point to a point that matches the next and/or with previous points. Point ID : [%d]";
    private static final String ATTEMPT_TO_UPDATE_FIRST_POINT_TO_CHIPPING_LOCATION_POINT =
            "Attempt to update first visited location to chipping location. Point ID : [%d]";


    @Override
    public List<VisitedDTOResponse> search(VisitedSearchSpec specification, Integer from, Integer size, Long animalId) {
        return visitedRepo
                .findAll(specification, Sort.by("id"))
                .stream()
                .filter(x -> x.getAnimal().getId().equals(animalId))
                .skip(from)
                .limit(size)
                .map(visitedDTOResponseMapper)
                .collect(Collectors.toList());
    }

    @Override
    public VisitedDTOResponse addVisitedPoint(Long animalId, Long pointId) {
        Animal animal = animalRepo
                .findById(animalId)
                .orElseThrow(() -> new NotFoundEx(animalId));
        Location location = locationRepo
                .findById(pointId)
                .orElseThrow(() -> new NotFoundEx(pointId));

        if(animal.getLifeStatus().equals(LifeStatus.DEAD))
            throw new BadRequestEx(String.format(ATTEMPT_TO_ADD_POINT_ANIMAL_DEAD_MSG, animalId));
        if(animal.getVisited().isEmpty()){
            if(animal.getChippingLocationId().getId().equals(pointId))
                throw new BadRequestEx(String.format(ATTEMPT_TO_ADD_FIRST_POINT_EQUALS_CHIPPING_LOCATION, pointId));
        } else {
            if(getCurrentVisitedPointId(animal).equals(location.getId()))
                throw new BadRequestEx(String.format(ATTEMPT_TO_ADD_POINT_EQUAL_TO_THE_CURRENT_ONE, pointId));
        }
        Visited visited = new Visited(animal, location, OffsetDateTime.now());
        return visitedDTOResponseMapper.apply(visitedRepo.save(visited));
    }

    @Override
    public VisitedDTOResponse updateAnimalVisited(UpdateVisitedDTO updateVisitedDTO, Long animalId) {
        Animal animal = animalRepo
                        .findById(animalId)
                        .orElseThrow(() -> new NotFoundEx(animalId));
        Visited visited = visitedRepo
                        .findByAnimalIdAndId(animalId,updateVisitedDTO.visitedLocationPointId())
                        .orElseThrow(() -> new NotFoundEx(updateVisitedDTO.visitedLocationPointId()));
        Location location = locationRepo
                        .findById(updateVisitedDTO.locationPointId())
                        .orElseThrow(() -> new NotFoundEx(updateVisitedDTO.locationPointId()));

        if(location.getId().equals(animal.getChippingLocationId().getId()))
            throw new BadRequestEx(String.format(ATTEMPT_TO_UPDATE_FIRST_POINT_TO_CHIPPING_LOCATION_POINT, location.getId()));
        if(location.getId().equals(visited.getLocation().getId()))
            throw new BadRequestEx(String.format(ATTEMPT_TO_ADD_POINT_EQUAL_TO_THE_CURRENT_ONE, location.getId()));
        if(animal.getVisited().stream().anyMatch(x -> x.getLocation().getId().equals(location.getId())))
            throw new BadRequestEx(String.format(ATTEMPT_TO_UPDATE_POINT_MATCHES_PREVIOUS_OR_NEXT, updateVisitedDTO.locationPointId()));
        visited.setLocation(location);
        return visitedDTOResponseMapper.apply(visitedRepo.save(visited));
    }

    @Override
    public void deleteAnimalVisited(Long animalId, Long visitedPointId) {
        Animal animal = animalRepo
                .findById(animalId)
                .orElseThrow(() -> new NotFoundEx(animalId));
        Visited visited = visitedRepo
                .findByAnimalIdAndId(animalId, visitedPointId)
                .orElseThrow(() -> new NotFoundEx(visitedPointId));

        if(animal.getVisited().size() > 1) {
            if(animal.getVisited().get(0).getLocation().getId().equals(visited.getLocation().getId())
            && animal.getVisited().get(1).getLocation().getId().equals(animal.getChippingLocationId().getId())){
                visitedRepo.delete(animal.getVisited().get(1));
                animal.getVisited().remove(1);
            }
        }
        visitedRepo.delete(visited);
        animal.getVisited().remove(visited);
        animalRepo.save(animal);
    }

    private Long getCurrentVisitedPointId(Animal animal) {
        if(animal.getVisited().isEmpty())
            return 0L;
        return animal.getVisited().get(animal.getVisited().size() - 1).getLocation().getId();
    }
}
