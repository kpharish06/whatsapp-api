package io.github.kpharish06.whatsappapi.service;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import io.github.kpharish06.whatsappapi.dto.EmojiReactionRequest;
import io.github.kpharish06.whatsappapi.dto.EmojiReactionResponse;
import io.github.kpharish06.whatsappapi.dto.MessageRequest;
import io.github.kpharish06.whatsappapi.dto.MessageResponse;
import io.github.kpharish06.whatsappapi.entity.EmojiReaction;

public interface MessageService {

	MessageResponse sendMessage(MessageRequest request, Long senderId);
	Page<MessageResponse> getMessages(Long conversationId, Pageable pageable, Long userId);
//	EmojiReaction reactToMessageWithEmoji(EmojiReactionRequest request, Long userId);
	EmojiReactionResponse reactToMessageWithEmoji(EmojiReactionRequest request, Long userId);
	void markMessageAsSeen(UUID messageId, Long userId);
	

}
