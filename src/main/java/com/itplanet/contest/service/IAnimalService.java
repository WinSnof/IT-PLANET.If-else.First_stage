package com.itplanet.contest.service;

import com.itplanet.contest.dto.AnimalDTORequest;
import com.itplanet.contest.dto.AnimalDTOResponse;
import com.itplanet.contest.dto.AnimalDtoUpdateRequest;
import com.itplanet.contest.dto.UpdateAnimalTypeDTO;
import com.itplanet.contest.specification.AnimalSearchSpec;

import java.util.List;

public interface IAnimalService {
    AnimalDTOResponse findById(Long id);

    List<AnimalDTOResponse> search(AnimalSearchSpec specification, Integer form, Integer size);

    AnimalDTOResponse save(AnimalDTORequest animalDTOResponse);

    AnimalDTOResponse update(AnimalDtoUpdateRequest animalDTOResponse, Long id);

    void delete(Long id);

    AnimalDTOResponse addType(Long animalId, Long typeId);

    AnimalDTOResponse updateAnimalType(UpdateAnimalTypeDTO updateAnimalTypeDTO, Long id);

    AnimalDTOResponse deleteAnimalType(Long animalId, Long typeId);
}
