package com.traceback.api.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.traceback.api.entity.Item;

@Repository
public interface ItemRepository extends JpaRepository<Item, Long>{
	
//	// "JOIN FETCH" tells Hibernate: "Do not run separate queries for these!"
//    @Query("SELECT i FROM Item i " +
//           "LEFT JOIN FETCH i.finder " + // Get the finder details
//           "LEFT JOIN FETCH i.claims c " + // Get the claims
//           "LEFT JOIN FETCH c.messages " + // Get the messages inside those claims
//           "WHERE i.id = :id")
//    Optional<Item> findItemWithDetails(@Param("id") Long id);
	
	Page<Item> findByStatus(String status, Pageable pageable);

	// 1. Find all items that are currently "UNCLAIMED"
    List<Item> findByStatus(String status);

    // 2. Filter items by Category (e.g., "Electronics", "Keys")
    List<Item> findByCategoryAndStatus(String category, String status);

    // 3. Search items by keyword in the title or location (Ignoring uppercase/lowercase)
    List<Item> findByTitleContainingIgnoreCaseOrLocationContainingIgnoreCase(String title, String location);
    
 // Updated search: Finds items matching keyword (title/location) AND category
    List<Item> findByCategoryAndTitleContainingIgnoreCaseOrCategoryAndLocationContainingIgnoreCase(
        String category1, String title, String category2, String location
    );
    
    List<Item> findByFinderId(Long finderId);
	
}
