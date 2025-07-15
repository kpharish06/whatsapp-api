package io.github.kpharish06.whatsappapi.service;

import java.util.List;
import java.util.UUID;

import io.github.kpharish06.whatsappapi.dto.EmojiReactionRequest;
import io.github.kpharish06.whatsappapi.dto.EmojiReactionResponse;

public interface EmojiReactionService {
    EmojiReactionResponse reactToMessage(EmojiReactionRequest request, Long userId); // ✅ FIXED
    List<EmojiReactionResponse> getReactionsForMessage(UUID messageId);
}
