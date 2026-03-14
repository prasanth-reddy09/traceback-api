package com.traceback.api.dto;

import java.time.LocalDateTime;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ClaimResponseDTO {
    private Long id;
    private String proofDescription;
    private String status;
    private LocalDateTime createdAt;

    // Item Info
    private Long itemId;
    private String itemTitle;
    private String itemImageUrl;

    // Claimant Info (The Loser)
    private Long loserId;
    private String loserName;

    // Finder Info
    private Long finderId;
    private String finderName;
}