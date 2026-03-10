package com.traceback.api.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.traceback.api.entity.Item;
import com.traceback.api.entity.User;
import com.traceback.api.repository.ItemRepository;
import com.traceback.api.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ItemService {
	
	private final ItemRepository itemRepository;
	private final UserRepository userRepository;
	
	// 1. Report a newly found item
    public Item reportFoundItem(Item item, Long finderId) {
        // Business Rule: Check if the user (finder) actually exists in the database
        User finder = userRepository.findById(finderId)
                .orElseThrow(() -> new RuntimeException("Finder not found with ID: " + finderId));

        // Link the item to the finder
        item.setFinder(finder);
        
        // Set the default status for a brand new item
        item.setStatus("UNCLAIMED");

        // Save to database
        return itemRepository.save(item);
    }

    // 2. Get all unclaimed items for the main feed
    public List<Item> getAllUnclaimedItems() {
        return itemRepository.findByStatus("UNCLAIMED");
    }

    // 3. Search items by keyword
    public List<Item> searchUnclaimedItems(String keyword) {
        // This uses the cool method we wrote in the Repository!
        return itemRepository.findByTitleContainingIgnoreCaseOrLocationContainingIgnoreCase(keyword, keyword);
    }
}
