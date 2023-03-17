package com.itplanet.contest.repository;

import com.itplanet.contest.entity.Visited;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;
import java.util.Optional;

public interface VisitedRepo extends JpaRepository<Visited, Long>, JpaSpecificationExecutor<Visited> {
    List<Visited> findByAnimalId(Long id);
    List<Visited> findByLocationId(Long id);
    Optional<Visited> findByAnimalIdAndId(Long animalId, Long visitedId);
}
