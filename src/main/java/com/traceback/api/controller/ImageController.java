package com.traceback.api.controller;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.traceback.api.service.ImageUploadService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/images")
@RequiredArgsConstructor
public class ImageController {

    private final ImageUploadService imageUploadService;

    // Example URL: GET http://localhost:8080/api/images/generate-signature
    @GetMapping("/generate-signature")
    public ResponseEntity<Map<String, Object>> getSignature() {
        return ResponseEntity.ok(imageUploadService.generateSignature());
    }
}