package io.github.kpharish06.whatsappapi.dto;

import java.time.LocalDateTime;
import java.util.List;

import io.github.kpharish06.whatsappapi.entity.ConversationType;
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
public class ConversationResponse {
    private Long id;
    private String name;
    private ConversationType type;
    private LocalDateTime createdAt;
    private Long createdBy;
    private boolean isAdmin;
    private List<ParticipantResponse> participants;   
}

