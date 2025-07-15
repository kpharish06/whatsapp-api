package io.github.kpharish06.whatsappapi.service;

import java.time.Instant;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.github.kpharish06.whatsappapi.dto.AttachmentResponse;
import io.github.kpharish06.whatsappapi.dto.EmojiReactionRequest;
import io.github.kpharish06.whatsappapi.dto.EmojiReactionResponse;
import io.github.kpharish06.whatsappapi.dto.MessageRequest;
import io.github.kpharish06.whatsappapi.dto.MessageResponse;
import io.github.kpharish06.whatsappapi.entity.Conversation;
import io.github.kpharish06.whatsappapi.entity.Emoji;
import io.github.kpharish06.whatsappapi.entity.EmojiReaction;
import io.github.kpharish06.whatsappapi.entity.Message;
import io.github.kpharish06.whatsappapi.entity.MessageStatus;
import io.github.kpharish06.whatsappapi.entity.UserProfile;
import io.github.kpharish06.whatsappapi.repository.ConversationRepository;
import io.github.kpharish06.whatsappapi.repository.EmojiReactionRepository;
import io.github.kpharish06.whatsappapi.repository.EmojiRepository;
import io.github.kpharish06.whatsappapi.repository.MessageRepository;
import io.github.kpharish06.whatsappapi.repository.MessageStatusRepository;
import io.github.kpharish06.whatsappapi.repository.UserProfileRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MessageServiceImpl implements MessageService {
	
	 private final SimpMessagingTemplate messagingTemplate;
	 private final MessageRepository messageRepository;
	 private final UserProfileRepository userRepository;
	 private final ConversationRepository conversationRepository;
	 private final MessageStatusRepository messageStatusRepository;
	 private final AttachmentService attachmentService;
	 private final EmojiReactionService emojiReactionService;
	 private final EmojiReactionRepository emojiReactionRepository;
	 private final EmojiRepository emojiRepository;
	 private final UserProfileRepository userProfileRepository;
	 @Transactional
	 @Override
	 public MessageResponse sendMessage(MessageRequest request, Long senderId) {
		
		 UserProfile sender = userRepository.getReferenceById(senderId);

		 Conversation conversation = conversationRepository.findById(request.getConversationId())
		            .orElseThrow(() -> new RuntimeException("Conversation not found"));
		
		 if ((request.getContent() == null || request.getContent().isBlank()) 
			        && (request.getAttachment() == null || request.getAttachment().isEmpty())) {
			        throw new IllegalArgumentException("Message must have text or attachment");
			    }
		 AttachmentResponse attachmentResponse = null;
		    if (request.getAttachment() != null && !request.getAttachment().isEmpty()) {
		        attachmentResponse = attachmentService.storeAttachment(request.getAttachment());
		    }
		 Message message = Message.builder()
				 .conversation(conversation)
		         .sender(sender)
		         .content(request.getContent())
		         .attachmentPath(attachmentResponse != null ? attachmentResponse.getPath() : null)
		         .attachmentType(attachmentResponse != null ? attachmentResponse.getType() : null)
		         .timestamp(Instant.now())
		         .build();
		 message = messageRepository.save(message);
		 
		 MessageStatus status = new MessageStatus();
		    status.setMessage(message);
		    status.setUser(sender);
		    status.setDeliveredAt(Instant.now());
		    messageStatusRepository.save(status);
		
	     return MessageResponse.builder()
				  .id(message.getId())
				  .conversationId(conversation.getId())
				  .senderId(sender.getId())
				  .senderName(sender.getProfileName())
				  .content(message.getContent())
				  .attachmentPath(message.getAttachmentPath())
				  .attachmentType(message.getAttachmentType())
				  .timestamp(message.getTimestamp())
				  .delivered(status.getDeliveredAt() != null)
				  .seen(status.getSeenAt() != null)
				  .deliveredAt(status.getDeliveredAt())
				  .seenAt(status.getSeenAt())
				  .build();

		 
	 }

	@Override
	public Page<MessageResponse> getMessages(Long conversationId, Pageable pageable, Long userId) {
		 Conversation conversation = conversationRepository.findById(conversationId)
			        .orElseThrow(() -> new RuntimeException("Conversation not found"));

			    Page<Message> messages = messageRepository.findByConversationIdOrderByTimestampDesc(conversationId, pageable);

			    return messages.map(message -> {
			        // Fetch or create status for this user
			        MessageStatus status = messageStatusRepository
			            .findByMessageIdAndUserId(message.getId(), userId)
			            .orElseGet(() -> {
			                MessageStatus s = new MessageStatus();
			                s.setMessage(message);
			                s.setUser(userRepository.getReferenceById(userId));
			                s.setDeliveredAt(Instant.now());
			                return messageStatusRepository.save(s);
			            });

			     return MessageResponse.builder()
			            .id(message.getId())
			            .conversationId(conversationId)
			            .senderId(message.getSender().getId())
			            .senderName(message.getSender().getProfileName())
			            .content(message.getContent())
			            .attachmentPath(message.getAttachmentPath())
			            .attachmentType(message.getAttachmentType())
			            .timestamp(message.getTimestamp())
			            .delivered(status.getDeliveredAt() != null)
			            .seen(status.getSeenAt() != null)
			            .deliveredAt(status.getDeliveredAt())
			            .seenAt(status.getSeenAt())
			            .build();
			    });
	}

	@Override
	@Transactional
	public EmojiReactionResponse reactToMessageWithEmoji(EmojiReactionRequest request, Long userId) {
		UserProfile user = userProfileRepository.getReferenceById(userId);

        Message message = messageRepository.findById(request.getMessageId())
                .orElseThrow(() -> new RuntimeException("Message not found"));

        Emoji emoji = emojiRepository.findByEmojiType(request.getEmojiType())
                .orElseThrow(() -> new RuntimeException("Emoji type not supported"));

        // Check if user already reacted to the message
        Optional<EmojiReaction> existingReaction =
                emojiReactionRepository.findByMessageAndUser(message, user);

        EmojiReaction reaction;
        if (existingReaction.isPresent()) {
            reaction = existingReaction.get();
            reaction.setEmoji(emoji); // update the emoji
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

        return EmojiReactionResponse.builder()
                .messageId(message.getId())
                .userId(user.getId())
                .emojiType(emoji.getEmojiType())
                .emojiDisplay(emoji.getEmojiDisplay())
                .reactedByUserName(user.getProfileName())
                .reactedAt(reaction.getReactedAt())
                .build();
        }
	
	
}
