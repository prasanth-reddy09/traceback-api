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

import com.traceback.api.dto.MessageRequest;
import com.traceback.api.entity.Message;
import com.traceback.api.service.MessageService;

import lombok.RequiredArgsConstructor;

/**
 * API endpoints for the private chat rooms between Finders and Losers.
 */
@RestController
@RequestMapping("/api/messages")
@RequiredArgsConstructor
public class MessageController {

    private final MessageService messageService;

    // 1. Send a new message
    // Example URL: POST http://localhost:8080/api/messages
    @PostMapping
    public ResponseEntity<Message> sendMessage(@RequestBody MessageRequest request) {
        Message newMessage = messageService.sendMessage(
                request.getClaimId(),
                request.getSenderId(),
                request.getContent()
        );
        return new ResponseEntity<>(newMessage, HttpStatus.CREATED);
    }

    // 2. Load the chat history for a specific claim
    // Example URL: GET http://localhost:8080/api/messages/1
    @GetMapping("/{claimId}")
    public ResponseEntity<List<Message>> getChatHistory(@PathVariable Long claimId) {
        return ResponseEntity.ok(messageService.getChatHistory(claimId));
    }
}