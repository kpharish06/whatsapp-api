package io.github.kpharish06.whatsappapi.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import io.github.kpharish06.whatsappapi.dto.ConversationRequest;
import io.github.kpharish06.whatsappapi.dto.ConversationResponse;

public interface ConversationService {

    ConversationResponse createConversation(ConversationRequest request, Long currentUserId);

    Page<ConversationResponse> getConversationsForUser(Long userId, Pageable pageable);

    void leaveGroup(Long conversationId, Long userId);

    void removeParticipant(Long conversationId, Long targetUserId, Long currentUserId);

    void updateMuteSetting(Long conversationId, Long userId, boolean muted);
}
