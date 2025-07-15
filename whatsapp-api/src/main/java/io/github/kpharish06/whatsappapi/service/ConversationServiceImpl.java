package io.github.kpharish06.whatsappapi.service;

import io.github.kpharish06.whatsappapi.dto.ConversationRequest;
import io.github.kpharish06.whatsappapi.dto.ConversationResponse;
import io.github.kpharish06.whatsappapi.entity.Conversation;
import io.github.kpharish06.whatsappapi.entity.ConversationParticipant;
import io.github.kpharish06.whatsappapi.entity.ConversationSettings;
import io.github.kpharish06.whatsappapi.entity.ConversationType;
import io.github.kpharish06.whatsappapi.entity.UserProfile;
import io.github.kpharish06.whatsappapi.repository.ConversationParticipantRepository;
import io.github.kpharish06.whatsappapi.repository.ConversationRepository;
import io.github.kpharish06.whatsappapi.repository.ConversationSettingsRepository;
import io.github.kpharish06.whatsappapi.repository.UserProfileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ConversationServiceImpl implements ConversationService {

    private final ConversationRepository conversationRepository;
    private final ConversationParticipantRepository participantRepository;
    private final ConversationSettingsRepository conversationSettingsRepository;
    private final UserProfileRepository userRepository;

    @Override
    public ConversationResponse createConversation(ConversationRequest request, Long currentUserId) {
       
    	UserProfile currentUser = userRepository.findById(currentUserId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        List<UserProfile> participants = userRepository.findAllById(request.getParticipantUserIds());

        if (request.getType() == ConversationType.DIRECT) {
            if (participants.size() != 1) {
                throw new IllegalArgumentException("create group chat");
            }

            Long otherUserId = participants.get(0).getId();
            Optional<Conversation> existing = conversationRepository.findDirectChatBetweenUsers(currentUserId, otherUserId);
            if (existing.isPresent()) {
                return toDto(existing.get(), currentUserId);
            }
        }

        Conversation conversation = Conversation.builder()
                .type(request.getType())
                .name(request.getType() == ConversationType.GROUP ? request.getName() : null)
                .createdAt(LocalDateTime.now())
                .createdBy(currentUser)
                .build();

        conversation = conversationRepository.save(conversation);

        
        saveParticipantAndSettings(conversation, currentUser);

        // Add other participants
        for (UserProfile user : participants) {
            if (!user.getId().equals(currentUserId)) {
                saveParticipantAndSettings(conversation, user);
            }
        }

        return toDto(conversation, currentUserId);
    }

    @Override
    public Page<ConversationResponse> getConversationsForUser(Long userId, Pageable pageable) {
        Page<Conversation> conversations = conversationRepository.findByParticipantUserId(userId, pageable);
        return conversations.map(convo -> toDto(convo, userId));
    }

    @Override
    public void leaveGroup(Long conversationId, Long userId) {
        ConversationParticipant participant = participantRepository
                .findByConversationIdAndUserId(conversationId, userId)
                .orElseThrow(() -> new RuntimeException("You are not part of this conversation"));

        participantRepository.delete(participant);
        conversationSettingsRepository.deleteByUserIdAndConversationId(userId, conversationId);
    }

    @Override
    public void removeParticipant(Long conversationId, Long targetUserId, Long currentUserId) {
        Conversation convo = conversationRepository.findById(conversationId)
                .orElseThrow(() -> new RuntimeException("Conversation not found"));

        if (!convo.getCreatedBy().getId().equals(currentUserId)) {
            throw new RuntimeException("Only the group creator can remove participants.");
        }

        ConversationParticipant participant = participantRepository
                .findByConversationIdAndUserId(conversationId, targetUserId)
                .orElseThrow(() -> new RuntimeException("Target user is not a participant"));

        participantRepository.delete(participant);
        conversationSettingsRepository.deleteByUserIdAndConversationId(targetUserId, conversationId);
    }

    @Override
    public void updateMuteSetting(Long conversationId, Long userId, boolean muted) {
        ConversationSettings settings = conversationSettingsRepository
                .findByUserIdAndConversationId(userId, conversationId)
                .orElseThrow(() -> new RuntimeException("ConversationSettings not found"));

        settings.setMuted(muted);
        conversationSettingsRepository.save(settings);
    }

    private ConversationResponse toDto(Conversation convo, Long currentUserId) {
        boolean isAdmin = convo.getType() == ConversationType.GROUP &&
                          convo.getCreatedBy().getId().equals(currentUserId);

        List<Long> participantIds = convo.getParticipants().stream()
                .map(p -> p.getUser().getId())
                .collect(Collectors.toList());

        return ConversationResponse.builder()
                .id(convo.getId())
                .name(convo.getName())
                .type(convo.getType())
                .createdAt(convo.getCreatedAt())
                .createdBy(convo.getCreatedBy().getId())
                .participantIds(participantIds)
                .isAdmin(isAdmin)
                .build();
    }

    private void saveParticipantAndSettings(Conversation conversation, UserProfile user) {
        participantRepository.save(
            ConversationParticipant.builder()
                .conversation(conversation)
                .user(user)
                .build()
        );

        conversationSettingsRepository.save(
            ConversationSettings.builder()
                .conversation(conversation)
                .user(user)
                .muted(false)
                .restrictedUserIds(new ArrayList<>())
                .build()
        );
    }
}