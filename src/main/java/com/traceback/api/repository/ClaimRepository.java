package com.traceback.api.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.traceback.api.entity.Claim;

@Repository
public interface ClaimRepository extends JpaRepository<Claim, Long> {

    // 1. For the Finder: See all people trying to claim their found item
    List<Claim> findByItemId(Long itemId);

    // 2. For the Loser: See all the claims they have made to track the status
//    List<Claim> findByLoserId(Long loserId);
    
 // 2. For My Claims: Finds claims where the current user is the LOSER
    @Query("SELECT c FROM Claim c " +
           "JOIN FETCH c.item i " +
           "JOIN FETCH i.finder f " +
           "JOIN FETCH c.loser l " +
           "WHERE l.id = :loserId")
    List<Claim> findByLoserId(@Param("loserId") Long loserId);

    // 3. ANTI-SPAM: Check if this exact user already claimed this exact item
    boolean existsByItemIdAndLoserId(Long itemId, Long loserId);
    
 // Finds claims where the item's finder is a specific user
//    List<Claim> findByItem_Finder_Id(Long finderId);
    
    @Query("SELECT c FROM Claim c " +
            "JOIN FETCH c.item i " +
            "JOIN FETCH i.finder f " +
            "JOIN FETCH c.loser l " +
            "WHERE f.id = :finderId")
     List<Claim> findByItem_Finder_Id(@Param("finderId") Long finderId);
}