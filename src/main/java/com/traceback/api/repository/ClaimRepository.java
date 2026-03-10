package com.traceback.api.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.traceback.api.entity.Claim;

@Repository
public interface ClaimRepository extends JpaRepository<Claim, Long> {

    // 1. For the Finder: See all people trying to claim their found item
    List<Claim> findByItemId(Long itemId);

    // 2. For the Loser: See all the claims they have made to track the status
    List<Claim> findByLoserId(Long loserId);

    // 3. ANTI-SPAM: Check if this exact user already claimed this exact item
    boolean existsByItemIdAndLoserId(Long itemId, Long loserId);
}