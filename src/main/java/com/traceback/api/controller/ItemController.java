package com.traceback.api.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.traceback.api.entity.Item;
import com.traceback.api.service.ItemService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/items")
@RequiredArgsConstructor
public class ItemController {

	private final ItemService itemService;
	
	// 1. Report a new found item
    // Example URL: POST http://localhost:8080/api/items/report/1
    @PostMapping("/report/{finderId}")
    public ResponseEntity<Item> reportItem(@PathVariable Long finderId, @RequestBody Item item) {
        Item savedItem = itemService.reportFoundItem(item, finderId);
        return new ResponseEntity<>(savedItem, HttpStatus.CREATED);
    }

    // 2. Get the main feed (all UNCLAIMED items)
    // Example URL: GET http://localhost:8080/api/items
    @GetMapping
    public ResponseEntity<List<Item>> getAllUnclaimedItems() {
        return ResponseEntity.ok(itemService.getAllUnclaimedItems());
    }

    // 3. Search for items
    // Example URL: GET http://localhost:8080/api/items/search?keyword=macbook
    @GetMapping("/search")
    public ResponseEntity<List<Item>> searchItems(@RequestParam String keyword) {
        return ResponseEntity.ok(itemService.searchUnclaimedItems(keyword));
    }
}
