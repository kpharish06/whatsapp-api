package io.github.kpharish06.whatsappapi.dto;

import java.util.UUID;

import lombok.Data;

@Data
public class EmojiReactionRequest {
    private UUID messageId;
    private Long conversationId;
    private String emojiType; 
    private Long userId;

}
