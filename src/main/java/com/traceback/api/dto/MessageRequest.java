package com.traceback.api.dto;

import lombok.Data;

@Data
public class MessageRequest {
    private Long claimId;
    private Long senderId;
    private String content;
}