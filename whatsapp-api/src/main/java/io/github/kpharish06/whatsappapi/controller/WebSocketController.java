package io.github.kpharish06.whatsappapi.controller;

import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import io.github.kpharish06.whatsappapi.dto.EmojiReactionRequest;
import io.github.kpharish06.whatsappapi.dto.EmojiReactionResponse;
import io.github.kpharish06.whatsappapi.dto.MessageRequest;
import io.github.kpharish06.whatsappapi.dto.MessageResponse;
import io.github.kpharish06.whatsappapi.entity.EmojiReaction;
import io.github.kpharish06.whatsappapi.service.MessageService;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class WebSocketController {
	
	private final MessageService messageService;
    private final SimpMessagingTemplate messagingTemplate;
    
    @MessageMapping("/send-message")
    public void handleMessage(@Payload MessageRequest request,@Header("sender-id") Long senderId) {
    	MessageResponse response = messageService.sendMessage(request, senderId);
    	messagingTemplate.convertAndSend("/topic/chatroom/" + request.getConversationId(), response);
    }
    @MessageMapping("/emoji")
    public void reactWithEmoji(@Payload EmojiReactionRequest request,
                               @Header("user-id") Long userId) {
        EmojiReactionResponse reaction = messageService.reactToMessageWithEmoji(request, userId);

        messagingTemplate.convertAndSend(
            "/topic/chatroom/" + request.getConversationId() + "/emoji", reaction);
    }

    
}
