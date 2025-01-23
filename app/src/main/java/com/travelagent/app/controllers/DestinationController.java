package com.travelagent.app.controllers;

import com.travelagent.app.models.Destination;
import com.travelagent.app.services.DestinationService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/destinations")
public class DestinationController {

    private final DestinationService destinationService;

    public DestinationController(DestinationService destinationService) {
        this.destinationService = destinationService;
    }

    @GetMapping
    public List<Destination> getAllDestinations() {
        return destinationService.getAllDestinations();
    }

    @GetMapping("/{id}")
    public Destination getDestinationById(@PathVariable Long id) {
        return destinationService.getDestinationById(id);
    }

    @PostMapping
    public Destination createDestination(@RequestBody Destination destination) {
        return destinationService.saveDestination(destination);
    }

    @DeleteMapping("/{id}")
    public void deleteDestination(@PathVariable Long id) {
        destinationService.deleteDestination(id);
    }
}
