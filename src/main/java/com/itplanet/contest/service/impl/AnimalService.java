package com.itplanet.contest.service.impl;

import com.itplanet.contest.dto.AnimalDTORequest;
import com.itplanet.contest.dto.AnimalDTOResponse;
import com.itplanet.contest.dto.AnimalDtoUpdateRequest;
import com.itplanet.contest.dto.UpdateAnimalTypeDTO;
import com.itplanet.contest.entity.*;
import com.itplanet.contest.entity.enumeration.Gender;
import com.itplanet.contest.entity.enumeration.LifeStatus;
import com.itplanet.contest.exception.BadRequestEx;
import com.itplanet.contest.exception.ConflictEx;
import com.itplanet.contest.exception.NotFoundEx;
import com.itplanet.contest.mapper.AnimalDTOResponseMapper;
import com.itplanet.contest.mapper.AnimalDTOUpdateResponseMapper;
import com.itplanet.contest.repository.*;
import com.itplanet.contest.service.IAnimalService;
import com.itplanet.contest.specification.AnimalSearchSpec;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class AnimalService implements IAnimalService {

    private final AnimalRepo animalRepo;
    private final AccountRepo accountRepo;
    private final LocationRepo locationRepo;
    private final TypeRepo typeRepo;
    private final VisitedRepo visitedRepo;
    private final AnimalDTOResponseMapper animalDTOResponseMapper;
    private final AnimalDTOUpdateResponseMapper animalDTOUpdateResponseMapper;
    private static final String LIST_CONTAINS_NON_UNIQUE_ELEMENTS = "The list contains non-unique elements";
    private static final String ATTEMPT_TO_RESURRECT_ANIMAL = "Animal dead. Attempt to resurrect animal. Animal ID : [%d]";
    private static final String ATTEMPT_TO_ADD_EXISTED_TYPE = "Animal already have type. Type : [%s]";
    private static final String ATTEMPT_TO_DELETE_SINGLE_TYPE = "Animal have the only one type.";


    @Override
    public AnimalDTOResponse findById(Long id) {
        return animalDTOResponseMapper.apply(animalRepo
                .findById(id)
                .orElseThrow(() -> new NotFoundEx(id)));
    }

    @Override
    public List<AnimalDTOResponse> search(AnimalSearchSpec specification, Integer from, Integer size) {
        return animalRepo
                .findAll(specification, Sort.by("id"))
                .stream()
                .skip(from)
                .limit(size)
                .map(animalDTOResponseMapper)
                .collect(Collectors.toList());
    }

    @Override
    public AnimalDTOResponse save(AnimalDTORequest animalDTOResponse) {
        if(animalDTOResponse.animalTypes().size() != animalDTOResponse.animalTypes().stream().distinct().count())
            throw new ConflictEx(LIST_CONTAINS_NON_UNIQUE_ELEMENTS);
        Account account = accountRepo
                .findById(animalDTOResponse.chipperId())
                .orElseThrow(() -> new NotFoundEx(animalDTOResponse.chipperId()));
        Location location = locationRepo
                .findById(animalDTOResponse.chippingLocationId())
                .orElseThrow(() -> new NotFoundEx(animalDTOResponse.chippingLocationId()));
        Set<Type> types = animalDTOResponse.animalTypes().stream()
                .map(x -> typeRepo.findById(x).orElseThrow(() -> new NotFoundEx(x)))
                .collect(Collectors.toSet());
        Animal animal = new Animal(
                types,
                animalDTOResponse.weight(),
                animalDTOResponse.length(),
                animalDTOResponse.height(),
                Gender.valueOf(animalDTOResponse.gender().toUpperCase()),
                account,
                location
        );
        return animalDTOResponseMapper.apply(animalRepo.save(animal));
    }

    @Override
    public AnimalDTOResponse update(AnimalDtoUpdateRequest animalDTOResponse, Long animalId) {
        Animal toUpdate = animalRepo
                .findById(animalId)
                .orElseThrow(() -> new NotFoundEx(animalId));
        if(toUpdate.getLifeStatus().equals(LifeStatus.DEAD) && animalDTOResponse.lifeStatus().equals(LifeStatus.ALIVE.name()))
            throw new NotFoundEx(String.format(ATTEMPT_TO_RESURRECT_ANIMAL, animalId));
        Account account = accountRepo.findById(animalDTOResponse.chipperId())
                .orElseThrow(() -> new NotFoundEx(animalDTOResponse.chipperId()));
        Location location = locationRepo.findById(animalDTOResponse.chippingLocationId())
                .orElseThrow(() -> new NotFoundEx(animalDTOResponse.chippingLocationId()));
        toUpdate.setWeight(animalDTOResponse.weight());
        toUpdate.setHeight(animalDTOResponse.height());
        toUpdate.setLength(animalDTOResponse.length());
        toUpdate.setGender(Gender.valueOf(animalDTOResponse.gender()));
        toUpdate.setChipperId(account);
        toUpdate.setChippingLocationId(location);
        if(!toUpdate.getVisited().isEmpty()) {
            if(toUpdate.getChippingLocationId().getId().equals(toUpdate.getVisited().get(0).getLocation().getId()))
                throw new BadRequestEx();
        }
        if(toUpdate.getVisited().size() > 1 && animalDTOResponse.chippingLocationId().equals(toUpdate.getVisited().get(1).getLocation().getId()))
            throw new BadRequestEx("New method");


        if(animalDTOResponse.lifeStatus().equals(LifeStatus.DEAD.name())){
            toUpdate.setLifeStatus(LifeStatus.valueOf(animalDTOResponse.lifeStatus()));
            toUpdate.setDeathDateTime(OffsetDateTime.now());
            return animalDTOUpdateResponseMapper.apply(animalRepo.save(toUpdate));
        }
        return animalDTOResponseMapper.apply(animalRepo.save(toUpdate));
    }

    @Override
    public void delete(Long id) {
        Animal animal = animalRepo
                .findById(id)
                .orElseThrow(() -> new NotFoundEx(id));
        Visited visited = new Visited();
        if(!animal.getVisited().isEmpty())
            throw new BadRequestEx();
        visitedRepo.delete(visited);
        animalRepo.delete(animal);
    }

    @Override
    public AnimalDTOResponse addType(Long animalId, Long typeId) {
        Animal animal = animalRepo
                .findById(animalId)
                .orElseThrow(() -> new NotFoundEx(animalId));
        Type type = typeRepo
                .findById(typeId)
                .orElseThrow(() -> new NotFoundEx(typeId));
        if(animal.getAnimalTypes().contains(type))
            throw new ConflictEx(String.format(ATTEMPT_TO_ADD_EXISTED_TYPE, type.getType()));
        animal.addType(type);
        return animalDTOResponseMapper.apply(animalRepo.save(animal));
    }

    @Override
    public AnimalDTOResponse updateAnimalType(UpdateAnimalTypeDTO updateAnimalTypeDTO, Long id) {
        Type oldType = typeRepo
                .findById(updateAnimalTypeDTO.oldTypeId())
                .orElseThrow(() -> new NotFoundEx(updateAnimalTypeDTO.oldTypeId()));
        Type newType = typeRepo
                .findById(updateAnimalTypeDTO.newTypeId())
                .orElseThrow(() -> new NotFoundEx(updateAnimalTypeDTO.newTypeId()));
        Animal animal = animalRepo
                .findById(id)
                .orElseThrow(() -> new NotFoundEx(id));
        if(!animal.getAnimalTypes().contains(oldType))
            throw new NotFoundEx(oldType.getId());
        if(animal.getAnimalTypes().contains(newType))
            throw new ConflictEx(String.format(ATTEMPT_TO_ADD_EXISTED_TYPE, newType.getType()));
        animal.getAnimalTypes().remove(oldType);
        animal.getAnimalTypes().add(newType);

        return animalDTOResponseMapper.apply(animalRepo.save(animal));
    }

    @Override
    public AnimalDTOResponse deleteAnimalType(Long animalId, Long typeId) {
        Animal animal = animalRepo
                .findById(animalId)
                .orElseThrow(() -> new NotFoundEx(animalId));
        Type type = typeRepo
                .findById(typeId)
                .orElseThrow(() -> new NotFoundEx(typeId));
        if(!animal.getAnimalTypes().contains(type))
            throw new NotFoundEx(typeId);
        if(animal.getAnimalTypes().size() == 1)
            throw new BadRequestEx(ATTEMPT_TO_DELETE_SINGLE_TYPE);
        animal.getAnimalTypes().remove(type);
        return animalDTOResponseMapper.apply(animalRepo.save(animal));
    }
}
