package com.traceback.api.dto;

import lombok.Data;

/**
 * DTO for receiving a new claim from the frontend.
 */
@Data
public class ClaimRequest {
    private Long itemId;
    private Long loserId;
    private String proofDescription;
}