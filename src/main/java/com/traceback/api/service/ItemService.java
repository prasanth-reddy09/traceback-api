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

    public List<Item> searchUnclaimedItems(String keyword, String category) {
        // If "All" is selected, just use the keyword search logic
        if (category == null || category.equalsIgnoreCase("All")) {
            return itemRepository.findByTitleContainingIgnoreCaseOrLocationContainingIgnoreCase(keyword, keyword);
        }
        
        // Otherwise, filter by both category and keyword
        return itemRepository.findByCategoryAndTitleContainingIgnoreCaseOrCategoryAndLocationContainingIgnoreCase(
            category, keyword, category, keyword
        );
    }
 // 4. Get a single item by its ID
    public Item getItemById(Long id) {
        return itemRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Item not found with ID: " + id));
    }
    
    public List<Item> getItemsByFinderId(Long finderId) {
        return itemRepository.findByFinderId(finderId);
    }
}
