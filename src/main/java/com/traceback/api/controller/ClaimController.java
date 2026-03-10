package com.traceback.api.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.traceback.api.dto.ClaimRequest;
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
}