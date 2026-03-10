package com.traceback.api.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.traceback.api.entity.Item;

@Repository
public interface ItemRepository extends JpaRepository<Item, Long>{

	// 1. Find all items that are currently "UNCLAIMED"
    List<Item> findByStatus(String status);

    // 2. Filter items by Category (e.g., "Electronics", "Keys")
    List<Item> findByCategoryAndStatus(String category, String status);

    // 3. Search items by keyword in the title or location (Ignoring uppercase/lowercase)
    List<Item> findByTitleContainingIgnoreCaseOrLocationContainingIgnoreCase(String title, String location);
	
}
