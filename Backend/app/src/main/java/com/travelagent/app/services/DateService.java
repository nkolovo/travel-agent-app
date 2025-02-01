package com.travelagent.app.services;

import com.travelagent.app.models.Item;
import com.travelagent.app.models.Itinerary;
import com.travelagent.app.models.Date;

import com.travelagent.app.repositories.DateRepository;
import com.travelagent.app.repositories.ItemRepository;
import com.travelagent.app.repositories.ItineraryRepository;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DateService {

    private final DateRepository dateRepository;
    private final ItemRepository itemRepository;

    public DateService(DateRepository dateRepository, ItemRepository itemRepository,
            ItineraryRepository itineraryRepository) {
        this.dateRepository = dateRepository;
        this.itemRepository = itemRepository;

    }

    public Date addItemToDate(Long dateId, Long itemId) {
        Optional<Date> dateOpt = dateRepository.findById(dateId);
        Optional<Item> itemOpt = itemRepository.findById(itemId);

        if (dateOpt.isPresent() && itemOpt.isPresent()) {
            Date date = dateOpt.get();
            Item item = itemOpt.get();
            date.getItems().add(item);
            return dateRepository.save(date); // Saves relationship
        }
        throw new RuntimeException("Date or Item not found!");
    }

    public Date removeItemFromDate(Long dateId, Long itemId) {
        Optional<Date> dateOpt = dateRepository.findById(dateId);
        Optional<Item> itemOpt = itemRepository.findById(itemId);

        if (dateOpt.isPresent() && itemOpt.isPresent()) {
            Date date = dateOpt.get();
            Item item = itemOpt.get();
            date.getItems().remove(item);
            return dateRepository.save(date); // Saves relationship
        }
        throw new RuntimeException("Date or Item not found!");
    }
}
