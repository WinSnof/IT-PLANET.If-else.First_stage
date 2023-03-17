package com.itplanet.contest.controller;

import com.itplanet.contest.dto.*;
import com.itplanet.contest.service.IAnimalService;
import com.itplanet.contest.service.ITypeService;
import com.itplanet.contest.service.IVisitedService;
import com.itplanet.contest.specification.AnimalSearchSpec;
import com.itplanet.contest.specification.VisitedSearchSpec;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/animals")
@AllArgsConstructor
@Validated
public class AnimalController {

    private final ITypeService typeService;
    private final IAnimalService animalService;
    private final IVisitedService visitedService;

    @GetMapping("types/{typeId}")
    ResponseEntity<TypeDTOResponse> findTypeById(@PathVariable("typeId") @Min(1) Long typeId) {
        return ResponseEntity.ok(typeService.findById(typeId));
    }

    @PostMapping("types")
    ResponseEntity<TypeDTOResponse> saveType(@RequestBody @Valid TypeDTORequest type) {
        return new ResponseEntity<>(typeService.save(type), HttpStatus.CREATED);
    }

    @PutMapping("types/{typeId}")
    ResponseEntity<TypeDTOResponse> updateType(@PathVariable("typeId") @Min(1) Long id,
                                @RequestBody @Valid TypeDTORequest type) {
        return ResponseEntity.ok(typeService.update(type, id));
    }

    @DeleteMapping("types/{typeId}")
    void deleteType(@PathVariable("typeId") @Min(1) Long id) {
        typeService.delete(id);
    }

    //API 5
    @GetMapping("{animalId}")
    ResponseEntity<AnimalDTOResponse> findAnimalById(@PathVariable("animalId") @Min(1) Long id) {
        return ResponseEntity.ok(animalService.findById(id));
    }

    @GetMapping("search")
    ResponseEntity<List<AnimalDTOResponse>> searchAnimals(AnimalSearchSpec specification,
                                                          @RequestParam(name = "from", defaultValue = "0") @Min(0) Integer from,
                                                          @RequestParam(name = "size", defaultValue = "10") @Min(1) Integer size) {
        return ResponseEntity.ok(animalService.search(specification, from, size));
    }

    @PostMapping
    ResponseEntity<AnimalDTOResponse> saveAnimal(@RequestBody @Valid AnimalDTORequest animalDTORequest) {
        return new ResponseEntity<>(animalService.save(animalDTORequest), HttpStatus.CREATED);
    }

    @PutMapping("{animalId}")
    ResponseEntity<AnimalDTOResponse> updateAnimal(@PathVariable("animalId") @Min(1) Long id,
                                                   @RequestBody @Valid AnimalDtoUpdateRequest animalDTOResponse) {
        return ResponseEntity.ok(animalService.update(animalDTOResponse, id));
    }

    @DeleteMapping("{animalId}")
    void deleteAnimal(@PathVariable("animalId") @Min(1) Long id) {
        animalService.delete(id);
    }

    @PostMapping("{animalId}/types/{typeId}")
    ResponseEntity<AnimalDTOResponse> addAnimalType(@PathVariable("animalId") @Min(1) Long animalId,
                                                    @PathVariable("typeId") @Min(1) Long typeId) {
        return new ResponseEntity(animalService.addType(animalId, typeId), HttpStatus.CREATED);
    }

    @PutMapping("{animalId}/types")
    ResponseEntity<AnimalDTOResponse> updateAnimalType(@RequestBody @Valid UpdateAnimalTypeDTO updateAnimalTypeDTO,
                                                       @PathVariable("animalId") @Min(1) Long id) {
        return ResponseEntity.ok(animalService.updateAnimalType(updateAnimalTypeDTO, id));
    }


    @DeleteMapping("{animalId}/types/{typeId}")
    ResponseEntity<AnimalDTOResponse> deleteAnimalType(@PathVariable("animalId") @Min(1) Long animalId,
                                                       @PathVariable("typeId") @Min(1) Long typeId) {
        return ResponseEntity.ok(animalService.deleteAnimalType(animalId, typeId));
    }

    //API 6
    @GetMapping("{animalId}/locations")
    ResponseEntity<List<VisitedDTOResponse>> searchVisitedLocation(VisitedSearchSpec specification,
                                                                   @RequestParam(name = "from", defaultValue = "0") @Min(0) Integer from,
                                                                   @RequestParam(name = "size", defaultValue = "10") @Min(1) Integer size,
                                                                   @PathVariable("animalId") Long animalId) {
        return ResponseEntity.ok(visitedService.search(specification, from, size, animalId));
    }

    @PostMapping("{animalId}/locations/{pointId}")
    ResponseEntity<VisitedDTOResponse> addAnimalVisitedPoint(@PathVariable("animalId") @Min(1) Long animalId,
                                                             @PathVariable("pointId") @Min(1) Long pointId) {
        return new ResponseEntity<>(visitedService.addVisitedPoint(animalId, pointId), HttpStatus.CREATED);
    }

    @PutMapping("{animalId}/locations")
    ResponseEntity<VisitedDTOResponse> updateAnimalVisited(@PathVariable("animalId") @Min(1) Long id,
                                                           @RequestBody @Valid UpdateVisitedDTO updateVisitedDTO) {
        return ResponseEntity.ok(visitedService.updateAnimalVisited(updateVisitedDTO, id));
    }

    @DeleteMapping("{animalId}/locations/{visitedPointId}")
    void deleteAnimalVisited(@PathVariable("animalId") @Min(1) Long animalId,
                             @PathVariable("visitedPointId") @Min(1) Long visitedPointId) {
        visitedService.deleteAnimalVisited(animalId, visitedPointId);
    }
}
