package com.itplanet.contest.controller;

import com.itplanet.contest.dto.LocationDTORequest;
import com.itplanet.contest.dto.LocationDTOResponse;
import com.itplanet.contest.service.ILocationService;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/locations")
@AllArgsConstructor
@Validated
public class LocationController {
    private final ILocationService locationService;

    @GetMapping("{pointId}")
    ResponseEntity<LocationDTOResponse> findLocationById(@PathVariable("pointId") @Min(1) Long id) {
        return ResponseEntity.ok(locationService.findById(id));
    }

    @PostMapping
    ResponseEntity<LocationDTOResponse> saveLocation(@RequestBody @Valid LocationDTORequest location) {
        return new ResponseEntity<>(locationService.save(location), HttpStatus.CREATED);
    }

    @PutMapping("{pointId}")
    ResponseEntity<LocationDTOResponse> updateLocation(@PathVariable("pointId") @Min(1) Long pointId,
                                                       @RequestBody @Valid LocationDTORequest location) {
        return ResponseEntity.ok(locationService.update(location, pointId));
    }

    @DeleteMapping("{pointId}")
    void deleteLocation(@PathVariable("pointId") @Min(1) Long pointId) {
        locationService.delete(pointId);
    }
}
