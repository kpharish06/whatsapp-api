package io.github.kpharish06.whatsappapi.dto;

import java.util.List;

import io.github.kpharish06.whatsappapi.entity.ConversationType;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
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
public class ConversationRequest {
	
	@NotNull
    private ConversationType type; 
	// DIRECT or GROUP

    // Optional for GROUP; ignored for DIRECT
    private String name;

    @NotEmpty
    private List<Long> participantUserIds; 

}
