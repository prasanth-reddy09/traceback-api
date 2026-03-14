package com.traceback.api.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.traceback.api.dto.ClaimResponseDTO;
import com.traceback.api.entity.Claim;
import com.traceback.api.entity.Item;
import com.traceback.api.entity.User;
import com.traceback.api.repository.ClaimRepository;
import com.traceback.api.repository.ItemRepository;
import com.traceback.api.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ClaimService {

    private final ClaimRepository claimRepository;
    private final ItemRepository itemRepository;
    private final UserRepository userRepository;

    public Claim createClaim(Long itemId, Long loserId, String proofDescription) {
        
        // 1. Fetch the Item and User from the database
        Item item = itemRepository.findById(itemId)
                .orElseThrow(() -> new RuntimeException("Item not found!"));
        User loser = userRepository.findById(loserId)
                .orElseThrow(() -> new RuntimeException("User not found!"));

        // 2. Business Rule: You cannot claim an item that you found yourself!
        if (item.getFinder().getId().equals(loserId)) {
            throw new RuntimeException("You cannot claim an item you reported as found.");
        }

        // 3. Business Rule (Anti-Spam): Prevent duplicate claims
        if (claimRepository.existsByItemIdAndLoserId(itemId, loserId)) {
            throw new RuntimeException("You have already submitted a claim for this item. Please wait for the finder to respond.");
        }

        // 4. Build and save the new claim
        Claim newClaim = Claim.builder()
                .item(item)
                .loser(loser)
                .proofDescription(proofDescription)
                .status("PENDING") // Always starts as pending
                .build();

        return claimRepository.save(newClaim);
    }

    // Allows a Finder to view all claims on a specific item
    public List<Claim> getClaimsForItem(Long itemId) {
        return claimRepository.findByItemId(itemId);
    }
    
 // 3. Resolve a claim (Approve or Reject)
    public Claim resolveClaim(Long claimId, Long finderId, String action) {
        
        Claim claim = claimRepository.findById(claimId)
                .orElseThrow(() -> new RuntimeException("Claim not found!"));
                
        Item item = claim.getItem();

        // 🛡️ SECURITY CHECK: Only the user who FOUND the item can approve/reject claims for it.
        if (!item.getFinder().getId().equals(finderId)) {
            throw new RuntimeException("Unauthorized: Only the finder can resolve this claim.");
        }

        if (action.equalsIgnoreCase("APPROVE")) {
            claim.setStatus("APPROVED");
            
            // Mark the item as completely resolved
            item.setStatus("RESOLVED");
            itemRepository.save(item);

            // Auto-reject any other people who tried to claim this same item
            List<Claim> allClaims = claimRepository.findByItemId(item.getId());
            for (Claim otherClaim : allClaims) {
                if (!otherClaim.getId().equals(claimId)) {
                    otherClaim.setStatus("REJECTED");
                    claimRepository.save(otherClaim);
                }
            }
        } else if (action.equalsIgnoreCase("REJECT")) {
            claim.setStatus("REJECTED");
        } else {
            throw new RuntimeException("Invalid action. Please use 'APPROVE' or 'REJECT'.");
        }

        return claimRepository.save(claim);
    }
    
//    public List<Claim> getClaimsByLoserId(Long loserId) {
//        return claimRepository.findByLoserId(loserId);
//    }
    
    public List<ClaimResponseDTO> getClaimsByLoserId(Long loserId) {
        List<Claim> claims = claimRepository.findByLoserId(loserId);
        
        // Map the entities to our DTOs so the frontend gets 'finderName' and 'itemTitle'
        return claims.stream()
                     .map(this::mapToDTO) // Using the same mapping function we wrote earlier
                     .collect(Collectors.toList());
    }
    
//    public List<Claim> getClaimsForFinder(Long finderId) {
//        return claimRepository.findByItem_Finder_Id(finderId);
//    }
    
    
    public List<ClaimResponseDTO> getClaimsForFinder(Long finderId) {
        List<Claim> claims = claimRepository.findByItem_Finder_Id(finderId);

        // Convert List<Claim> to List<ClaimResponseDTO>
        return claims.stream().map(this::mapToDTO).collect(Collectors.toList());
    }

    private ClaimResponseDTO mapToDTO(Claim claim) {
        return ClaimResponseDTO.builder()
                .id(claim.getId())
                .proofDescription(claim.getProofDescription())
                .status(claim.getStatus())
                .createdAt(claim.getCreatedAt())
                // Item details
                .itemId(claim.getItem().getId())
                .itemTitle(claim.getItem().getTitle())
                .itemImageUrl(claim.getItem().getImageUrl())
                .proofDescription(claim.getItem().getDescription())
                // Loser (Claimant) details - using .getName() from your User entity
                .loserId(claim.getLoser().getId())
                .loserName(claim.getLoser().getName()) 
                // Finder details
                .finderId(claim.getItem().getFinder().getId())
                .finderName(claim.getItem().getFinder().getName())
                .build();
    }
}