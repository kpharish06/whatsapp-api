package io.github.kpharish06.whatsappapi.dto;

import java.time.Instant;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EmojiReactionResponse {
    private UUID messageId;
    private Long userId;
    private String emojiType;
    private String emojiDisplay;
    private String reactedByUserName;
    private Instant reactedAt;
}