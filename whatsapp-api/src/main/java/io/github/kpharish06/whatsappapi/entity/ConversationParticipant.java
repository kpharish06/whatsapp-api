package io.github.kpharish06.whatsappapi.entity;

import java.time.LocalDateTime;


import io.github.kpharish06.whatsappapi.dto.ConversationResponse;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "conversation_participants")
@Getter
@Setter
@NoArgsConstructor 
@AllArgsConstructor 
@Builder
public class ConversationParticipant {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne
	private Conversation conversation;

	@ManyToOne
	private UserProfile user;
	
	private boolean isMuted;
	
	private boolean isBlocked; 


	private LocalDateTime joinedAt;


	public ConversationResponse getUserProfile() {
		// TODO Auto-generated method stub
		return null;
	}
}
