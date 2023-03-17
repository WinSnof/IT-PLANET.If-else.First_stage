package com.itplanet.contest.repository;

import com.itplanet.contest.entity.Animal;
import com.itplanet.contest.entity.Type;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface AnimalRepo extends JpaRepository<Animal, Long>, JpaSpecificationExecutor<Animal> {
    List<Animal> findByChipperIdId(Long id);
    List<Animal> findByChippingLocationIdId(Long id);
    List<Animal> findByAnimalTypes(Type type);
}
