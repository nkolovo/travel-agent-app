package com.travelagent.app.repositories;

import com.travelagent.app.models.Destination;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DestinationRepository extends JpaRepository<Destination, Long> {
    Optional<Destination> findById(Long id);
}
