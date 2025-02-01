package com.travelagent.app.controllers;

import com.travelagent.app.models.Itinerary;
import com.travelagent.app.services.ItineraryService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/itineraries")
public class ItineraryController {

    private final ItineraryService itineraryService;

    public ItineraryController(ItineraryService itineraryService) {
        this.itineraryService = itineraryService;
    }

    @GetMapping
    public List<Itinerary> getAllItineraries() {
        return itineraryService.getAllItineraries();
    }

    @GetMapping("/{id}")
    public Itinerary getItineraryById(@PathVariable Long id) {
        return itineraryService.getItineraryById(id);
    }

    @PostMapping
    public Itinerary createItinerary(@RequestBody Itinerary itinerary) {
        return itineraryService.saveItinerary(itinerary);
    }

    @DeleteMapping("/{id}")
    public void deleteItinerary(@PathVariable Long id) {
        itineraryService.deleteItinerary(id);
    }
}
