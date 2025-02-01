package com.travelagent.app.services;

import com.travelagent.app.models.Itinerary;
import com.travelagent.app.repositories.ItineraryRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ItineraryService {

    private final ItineraryRepository itineraryRepository;

    public ItineraryService(ItineraryRepository itineraryRepository) {
        this.itineraryRepository = itineraryRepository;
    }

    public List<Itinerary> getAllItineraries() {
        return itineraryRepository.findAll();
    }

    public Itinerary getItineraryById(Long id) {
        return itineraryRepository.findById(id).orElseThrow(() -> new RuntimeException("Itinerary not found"));
    }

    public Itinerary saveItinerary(Itinerary itinerary) {
        return itineraryRepository.save(itinerary);
    }

    public void deleteItinerary(Long id) {
        itineraryRepository.deleteById(id);
    }
}
