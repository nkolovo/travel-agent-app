package com.travelagent.app.controllers;

import com.travelagent.app.models.Client;
import com.travelagent.app.models.Date;
import com.travelagent.app.models.Itinerary;

import com.travelagent.app.services.ClientService;
import com.travelagent.app.services.ItineraryService;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

@RestController
@RequestMapping("/api/itineraries")
public class ItineraryController {

    private final ItineraryService itineraryService;
    private final ClientService clientService;

    public ItineraryController(ItineraryService itineraryService, ClientService clientService) {
        this.itineraryService = itineraryService;
        this.clientService = clientService;
    }

    @GetMapping
    public List<Itinerary> getAllItineraries() {
        return itineraryService.getAllItineraries();
    }

    @GetMapping("/{id}")
    public Itinerary getItineraryById(@PathVariable Long id) {
        return itineraryService.getItineraryById(id);
    }

    @PostMapping("/create")
    public ResponseEntity<String> createItinerary(@RequestBody Itinerary itinerary) {
        try {
            System.out.println("Reached create api");

            // Handle the client creation if not already existing
            if (itinerary.getClient() != null && itinerary.getClient().getId() == null) {
                Client newClient = itinerary.getClient();
                Client savedClient = clientService.saveClient(newClient);
                itinerary.setClient(savedClient);
            }

            // Ensure that the dates list is initialized if not provided
            if (itinerary.getDates() == null) {
                itinerary.setDates(new ArrayList<>());
            }

            // If image is not provided, make sure it is set to null
            if (itinerary.getImage() != null && itinerary.getImage().length > 0) {
                byte[] decodedImage = Base64.getDecoder().decode(itinerary.getImage());
                itinerary.setImage(decodedImage);
            } else {
                itinerary.setImage(null); // Set image to null if not provided
            }

            // Save the itinerary to the database
            itineraryService.saveItinerary(itinerary);

            return ResponseEntity.ok("Itinerary created successfully.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error creating itinerary: " + e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public void deleteItinerary(@PathVariable Long id) {
        itineraryService.deleteItinerary(id);
    }

    @GetMapping("latest-reservation")
    public String getLatestReservation() {
        String lastReservation = itineraryService.getLatestReservationNumber();
        int lastNumber = Integer.parseInt(lastReservation.replaceAll("^PG", ""));
        int newNumber = lastNumber + 1;
        return String.format("PG%06d", newNumber);
    }

    @PostMapping("/update/name/{id}")
    public String updateItineraryName(@PathVariable Long id, @RequestBody String name) {
        Itinerary itinerary = itineraryService.getItineraryById(id);
        itinerary.setName(name);
        itineraryService.saveItinerary(itinerary);
        return "Itinerary name updated.";
    }

    // Upload Image (Accepts Any Image Format)
    @PostMapping("/{id}/upload-image")
    public ResponseEntity<String> uploadImage(@PathVariable Long id, @RequestParam("file") MultipartFile file) {
        try {
            String contentType = file.getContentType();
            if (contentType == null || !contentType.startsWith("image/")) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid file type! Only images allowed.");
            }

            Itinerary itinerary = itineraryService.getItineraryById(id);
            itinerary.setImage(file.getBytes());
            itinerary.setImageType(file.getContentType()); // Save MIME type
            itineraryService.saveItinerary(itinerary);
            return ResponseEntity.ok("Image uploaded successfully!");
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error uploading image");
        }
    }

    // Retrieve Image (Returns Correct Format)
    @GetMapping("/{id}/image")
    public ResponseEntity<byte[]> getImage(@PathVariable Long id) {
        Itinerary itinerary = itineraryService.getItineraryById(id);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_TYPE, itinerary.getImageType()) // Dynamically set MIME type
                .body(itinerary.getImage());

    }

    @PostMapping("/add/itinerary/{itineraryId}")
    public Date AddDateToItinerary(@PathVariable Long itineraryId, @RequestBody Date date) {
        return itineraryService.addDateToItinerary(itineraryId, date);
    }

    @PostMapping("/remove/{dateId}/itinerary/{itineraryId}")
    public Date RemoveDateFromItinerary(@PathVariable Long dateId, @PathVariable Long itineraryId) {
        return itineraryService.removeDateFromItinerary(dateId, itineraryId);
    }
}
