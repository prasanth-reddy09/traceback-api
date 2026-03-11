package com.traceback.api.dto;

import java.time.LocalDateTime;

import lombok.Builder;
import lombok.Data;

/**
 * A clean, lightweight object to broadcast over WebSockets.
 */
@Data
@Builder
public class MessageDto {
    private Long id;
    private Long claimId;
    private Long senderId;
    private String senderName;
    private String content;
    private LocalDateTime sentAt;
}