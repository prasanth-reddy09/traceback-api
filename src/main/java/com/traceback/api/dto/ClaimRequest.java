package com.traceback.api.dto;

import lombok.Data;

@Data
public class ClaimRequest {
    private Long itemId;
    private Long loserId;
    private String proofDescription;
}