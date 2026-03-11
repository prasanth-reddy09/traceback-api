package com.traceback.api.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AuthResponse {
    private String token; // The magical JWT!
    private Long userId;  // Helpful for the Next.js frontend to know who logged in
    private String name;
}