package com.itplanet.contest.repository;

import com.itplanet.contest.entity.Type;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TypeRepo extends JpaRepository<Type, Long> {
    Optional<Type> findByType(String type);
}
