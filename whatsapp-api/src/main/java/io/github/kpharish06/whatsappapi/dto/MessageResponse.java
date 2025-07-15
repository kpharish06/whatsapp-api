package io.github.kpharish06.whatsappapi.dto;

import java.time.Instant;

import java.util.UUID;

import io.github.kpharish06.whatsappapi.entity.AttachmentType;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MessageResponse {

    private UUID id;
    private Long conversationId;

    private Long senderId;
    private String senderName;

    private String content;
    private String attachmentPath;
    private AttachmentType attachmentType;

    private Instant timestamp;
	
    private boolean delivered;
    private boolean seen;
    private Instant deliveredAt;
    private Instant seenAt;
}
