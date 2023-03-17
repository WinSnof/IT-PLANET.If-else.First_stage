package com.itplanet.contest.mapper;

import com.itplanet.contest.dto.AnimalDTOResponse;
import com.itplanet.contest.entity.Animal;
import com.itplanet.contest.entity.BaseEntity;
import org.springframework.stereotype.Service;

import java.time.format.DateTimeFormatter;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class AnimalDTOResponseMapper implements Function<Animal, AnimalDTOResponse> {

    private final DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSXXX");

    @Override
    public AnimalDTOResponse apply(Animal animal) {
        return new AnimalDTOResponse(
                animal.getId(),
                animal.getAnimalTypes().stream().map(BaseEntity::getId).collect(Collectors.toList()),
                animal.getWeight(),
                animal.getLength(),
                animal.getHeight(),
                animal.getGender().name(),
                animal.getLifeStatus().name(),
                animal.getChippingDateTime().format(dtf),
                animal.getChipperId().getId(),
                animal.getChippingLocationId().getId(),
                animal.getVisited().stream().map(BaseEntity::getId).collect(Collectors.toList()),
                null
        );
    }
}
