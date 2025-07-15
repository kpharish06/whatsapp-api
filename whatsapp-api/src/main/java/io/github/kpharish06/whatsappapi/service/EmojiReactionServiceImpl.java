package io.github.kpharish06.whatsappapi.service;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.lang.Long;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.github.kpharish06.whatsappapi.dto.EmojiReactionRequest;
import io.github.kpharish06.whatsappapi.dto.EmojiReactionResponse;
import io.github.kpharish06.whatsappapi.entity.Emoji;
import io.github.kpharish06.whatsappapi.entity.EmojiReaction;
import io.github.kpharish06.whatsappapi.entity.Message;
import io.github.kpharish06.whatsappapi.entity.UserProfile;
import io.github.kpharish06.whatsappapi.repository.EmojiReactionRepository;
import io.github.kpharish06.whatsappapi.repository.EmojiRepository;
import io.github.kpharish06.whatsappapi.repository.MessageRepository;
import io.github.kpharish06.whatsappapi.repository.UserProfileRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EmojiReactionServiceImpl implements EmojiReactionService{
	
	private final EmojiReactionRepository emojiReactionRepository;
    private final UserProfileRepository userProfileRepository;
    private final MessageRepository messageRepository;
    private final EmojiRepository emojiRepository;
    private final SimpMessagingTemplate messagingTemplate;
    
    @Override
    @Transactional
    public EmojiReactionResponse reactToMessage(EmojiReactionRequest request, Long userId) {
        UserProfile user = userProfileRepository.getReferenceById(userId);

        Message message = messageRepository.findById(request.getMessageId())
                .orElseThrow(() -> new RuntimeException("Message not found"));

        Emoji emoji = emojiRepository.findByEmojiType(request.getEmojiType())
                .orElseThrow(() -> new RuntimeException("Emoji type not supported"));

        Optional<EmojiReaction> existing = emojiReactionRepository.findByMessageAndUser(message, user);

        EmojiReaction reaction;
        if (existing.isPresent()) {
            reaction = existing.get();
            reaction.setEmoji(emoji); 
            reaction.setReactedAt(Instant.now());
        } else {
            reaction = EmojiReaction.builder()
                    .message(message)
                    .user(user)
                    .emoji(emoji)
                    .reactedAt(Instant.now())
                    .build();
        }

        emojiReactionRepository.save(reaction);

        // emoji reaction via WebSocket
        EmojiReactionResponse response = EmojiReactionResponse.builder()
                .messageId(message.getId())
                .emojiType(emoji.getEmojiType())
                .emojiDisplay(emoji.getEmojiDisplay())
                .userId(user.getId())
                .reactedByUserName(user.getProfileName())
                .build();

        messagingTemplate.convertAndSend("/topic/chatroom/" + request.getConversationId() + "/emoji", response);
        return response;
    }

    @Override
    @Transactional(readOnly = true)
    public List<EmojiReactionResponse> getReactionsForMessage(UUID messageId) {
        Message message = messageRepository.findById(messageId)
                .orElseThrow(() -> new RuntimeException("Message not found"));

        List<EmojiReaction> reactions = emojiReactionRepository.findByMessage(message);

        return reactions.stream()
                .map(reaction -> EmojiReactionResponse.builder()
                		.messageId(messageId)
                        .userId(reaction.getUser().getId())
                        .emojiType(reaction.getEmoji().getEmojiType())
                        .emojiDisplay(reaction.getEmoji().getEmojiDisplay())
                        .reactedByUserName(reaction.getUser().getProfileName())
                        .reactedAt(reaction.getReactedAt())
                        .build()
                    ).toList();
    }

	
	

}
