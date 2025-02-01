package com.travelagent.app.repositories;

import com.travelagent.app.models.Itinerary;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ItineraryRepository extends JpaRepository<Itinerary, Long> {
    Optional<Itinerary> findById(Long id);
}
