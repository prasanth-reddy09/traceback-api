package com.traceback.api.service;

import java.util.List;

import org.springframework.messaging.simp.SimpMessagingTemplate; // <-- The Megaphone!
import org.springframework.stereotype.Service;

import com.traceback.api.dto.MessageDto;
import com.traceback.api.entity.Claim;
import com.traceback.api.entity.Message;
import com.traceback.api.entity.User;
import com.traceback.api.repository.ClaimRepository;
import com.traceback.api.repository.MessageRepository;
import com.traceback.api.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MessageService {

    private final MessageRepository messageRepository;
    private final ClaimRepository claimRepository;
    private final UserRepository userRepository;
    private final SimpMessagingTemplate messagingTemplate; // <-- Inject it here!

    public Message sendMessage(Long claimId, Long senderId, String content) {
        Claim claim = claimRepository.findById(claimId)
                .orElseThrow(() -> new RuntimeException("Claim not found!"));
        
        User sender = userRepository.findById(senderId)
                .orElseThrow(() -> new RuntimeException("User not found!"));

        // Security check
        Long finderId = claim.getItem().getFinder().getId();
        Long loserId = claim.getLoser().getId();

        if (!senderId.equals(finderId) && !senderId.equals(loserId)) {
            throw new RuntimeException("Unauthorized: You are not part of this private claim.");
        }

        // 1. Save to the database
        Message message = Message.builder()
                .claim(claim)
                .sender(sender)
                .content(content)
                .build();
        Message savedMessage = messageRepository.save(message);

        // 2. Build the lightweight DTO
        MessageDto messageDto = MessageDto.builder()
                .id(savedMessage.getId())
                .claimId(claim.getId())
                .senderId(sender.getId())
                .senderName(sender.getName())
                .content(savedMessage.getContent())
                .sentAt(savedMessage.getSentAt())
                .build();

        // 3. BROADCAST TO THE RADIO STATION! 📻
        // Anyone listening to "/topic/chat/{claimId}" will instantly get this JSON.
        messagingTemplate.convertAndSend("/topic/chat/" + claimId, messageDto);

        return savedMessage;
    }

    public List<Message> getChatHistory(Long claimId) {
        return messageRepository.findByClaimIdOrderBySentAtAsc(claimId);
    }
}