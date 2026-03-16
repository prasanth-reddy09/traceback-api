package com.traceback.api.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
	
    public Item reportFoundItem(Item item, Long finderId) {
    	
        User finder = userRepository.findById(finderId)
                .orElseThrow(() -> new RuntimeException("Finder not found with ID: " + finderId));

        item.setFinder(finder);
        
        item.setStatus("UNCLAIMED");

        return itemRepository.save(item);
    }

    // 2. Get all unclaimed items for the main feed
    public List<Item> getAllUnclaimedItems() {
        return itemRepository.findByStatus("UNCLAIMED");
        
    }
    
    public Page<Item> getAllUnclaimedItemsPaged(Pageable pageable) {
        return itemRepository.findByStatus("UNCLAIMED", pageable);
    }

    public List<Item> searchUnclaimedItems(String keyword, String category) {
        if (category == null || category.equalsIgnoreCase("All")) {
            return itemRepository.findByTitleContainingIgnoreCaseOrLocationContainingIgnoreCase(keyword, keyword);
        }
        
        return itemRepository.findByCategoryAndTitleContainingIgnoreCaseOrCategoryAndLocationContainingIgnoreCase(
            category, keyword, category, keyword
        );
    }
    
    
    public Item getItemById(Long id) {
//    	return itemRepository.findItemWithDetails(id)
//                .orElseThrow(() -> new RuntimeException("Item not found with id: " + id));
        return itemRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Item not found with ID: " + id));
    }
    
    public List<Item> getItemsByFinderId(Long finderId) {
        return itemRepository.findByFinderId(finderId);
    }
}
