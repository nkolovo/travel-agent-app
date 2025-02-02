package com.travelagent.app.services;

import com.travelagent.app.models.Date;
import com.travelagent.app.models.Itinerary;
import com.travelagent.app.repositories.DateRepository;
import com.travelagent.app.repositories.ItineraryRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class ItineraryService {

    private final ItineraryRepository itineraryRepository;
    private final DateRepository dateRepository;

    public ItineraryService(ItineraryRepository itineraryRepository, DateRepository dateRepository) {
        this.itineraryRepository = itineraryRepository;
        this.dateRepository = dateRepository;
    }

    public List<Itinerary> getAllItineraries() {
        return itineraryRepository.findAll();
    }

    public Itinerary getItineraryById(Long id) {
        return itineraryRepository.findById(id).orElseThrow(() -> new RuntimeException("Itinerary not found"));
    }

    public String getLatestReservationNumber() {
        return itineraryRepository.findNewestReservationNumber();
    }

    public Itinerary saveItinerary(Itinerary itinerary) {
        return itineraryRepository.save(itinerary);
    }

    public void deleteItinerary(Long id) {
        itineraryRepository.deleteById(id);
    }

    public Date addDateToItinerary(Long itineraryId, Date date) {
        Date newDate = dateRepository.save(date);
        Optional<Itinerary> itineraryOpt = itineraryRepository.findById(itineraryId);
        if (itineraryOpt.isPresent()) {
            Itinerary itinerary = itineraryOpt.get();
            List<Date> itineraryDates = itinerary.getDates();
            itineraryDates.add(newDate);
            itinerary.setDates(itineraryDates);
            return newDate;
        }
        throw new RuntimeException("Could not save date to itinerary");
    }

    public Date removeDateFromItinerary(Long dateId, Long itineraryId) {
        Optional<Itinerary> itineraryOpt = itineraryRepository.findById(itineraryId);
        if (itineraryOpt.isPresent()) {
            Itinerary itinerary = itineraryOpt.get();
            List<Date> dates = itinerary.getDates();

            // Find the date that matches the dateId and remove it
            Date dateToRemove = dates.stream()
                    .filter(date -> date.getId().equals(dateId))
                    .findFirst()
                    .orElse(null);

            if (dateToRemove != null) {
                dates.remove(dateToRemove);
                itinerary.setDates(dates);
                return dateToRemove;
            }
        }
        throw new RuntimeException("Could not remove date from itinerary");
    }
}
