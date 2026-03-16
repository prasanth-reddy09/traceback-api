package com.traceback.api.controller;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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
    @PostMapping("/report/{finderId}")
    public ResponseEntity<Item> reportItem(@PathVariable Long finderId, @RequestBody Item item) {
        Item savedItem = itemService.reportFoundItem(item, finderId);
        return new ResponseEntity<>(savedItem, HttpStatus.CREATED);
    }

    // 2. Get the main feed (all UNCLAIMED items)
//    @GetMapping
//    public ResponseEntity<List<Item>> getAllUnclaimedItems() {
//        return ResponseEntity.ok(itemService.getAllUnclaimedItems());
//    }

   
 // 3. Search for items with category support
 @GetMapping("/search")
 public ResponseEntity<List<Item>> searchItems(
         @RequestParam(required = false) String keyword,
         @RequestParam(required = false) String category) {
     
     // Default keyword to empty string if null to avoid JPA errors
     String searchKeyword = (keyword == null) ? "" : keyword;
     
     return ResponseEntity.ok(itemService.searchUnclaimedItems(searchKeyword, category));
 }
    
 // 4. Get a single item by ID
    @GetMapping("/{id}")
    public ResponseEntity<Item> getItemById(@PathVariable Long id) {
        return ResponseEntity.ok(itemService.getItemById(id));
    }
    
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Item>> getItemsByUser(@PathVariable Long userId) {
        return ResponseEntity.ok(itemService.getItemsByFinderId(userId));
    }
    
    @GetMapping
    public ResponseEntity<Page<Item>> getAllUnclaimedItems(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        
        Pageable pageable = PageRequest.of(page, size, Sort.by("createdAt").descending());
        return ResponseEntity.ok(itemService.getAllUnclaimedItemsPaged(pageable));
    }
}
