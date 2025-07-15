package io.github.kpharish06.whatsappapi.dto;

import java.util.UUID;

import io.github.kpharish06.whatsappapi.entity.MessageSentStatus;
import lombok.Data;

@Data
public class MessageStatusUpdateRequest {
	 private UUID messageId;
	 private Long conversationId;
	 private MessageSentStatus type; 
	
}
