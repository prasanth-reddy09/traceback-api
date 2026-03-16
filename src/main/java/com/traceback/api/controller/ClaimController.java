package com.traceback.api.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.traceback.api.dto.ClaimRequest;
import com.traceback.api.dto.ClaimResponseDTO;
import com.traceback.api.entity.Claim;
import com.traceback.api.service.ClaimService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/claims")
@RequiredArgsConstructor
public class ClaimController {

    private final ClaimService claimService;

    // 1. Submit a new claim
    @PostMapping
    public ResponseEntity<Claim> submitClaim(@RequestBody ClaimRequest request) {
        Claim newClaim = claimService.createClaim(
                request.getItemId(), 
                request.getLoserId(), 
                request.getProofDescription()
        );
        return new ResponseEntity<>(newClaim, HttpStatus.CREATED);
    }

    // 2. View all claims for a specific item
    @GetMapping("/item/{itemId}")
    public ResponseEntity<List<Claim>> getClaimsForItem(@PathVariable Long itemId) {
        return ResponseEntity.ok(claimService.getClaimsForItem(itemId));
    }
    
   // 3. Resolve a claim (Approve or Reject)
    @PutMapping("/{claimId}/resolve")
    public ResponseEntity<Claim> resolveClaim(
            @PathVariable Long claimId,
            @RequestParam Long finderId,
            @RequestParam String action) {
            
        Claim resolvedClaim = claimService.resolveClaim(claimId, finderId, action);
        return ResponseEntity.ok(resolvedClaim);
    }
    
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<ClaimResponseDTO>> getClaimsByUser(@PathVariable Long userId) {
        return ResponseEntity.ok(claimService.getClaimsByLoserId(userId));
    }
    
    @GetMapping("/finder/{finderId}")
    public ResponseEntity<List<ClaimResponseDTO>> getClaimsForFinder(@PathVariable Long finderId) {
        return ResponseEntity.ok(claimService.getClaimsForFinder(finderId));
    }
}