package com.traceback.api.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.traceback.api.entity.Message;

@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {

    // Fetch the entire chat history for a specific room, in chronological order!
    List<Message> findByClaimIdOrderBySentAtAsc(Long claimId);
}