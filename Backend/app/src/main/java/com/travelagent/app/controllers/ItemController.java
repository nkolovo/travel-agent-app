package com.travelagent.app.controllers;

import com.travelagent.app.models.Item;

import com.travelagent.app.services.ItemService;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/api/items")
public class ItemController {

    private final ItemService itemService;

    public ItemController(ItemService itemService) {
        this.itemService = itemService;
    }

    @PostMapping("/add")
    public String AddItem(@RequestBody Item item) {
        if (itemService.addItem(item))
            return "Item successfully added";
        return "Could not add item";
    }

    @PostMapping("/remove/{id}")
    public String removeItem(@PathVariable Long id) {
        itemService.removeItem(id);
        return "Item successfully removed";
    }
}
