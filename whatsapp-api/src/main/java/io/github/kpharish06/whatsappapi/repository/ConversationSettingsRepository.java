package io.github.kpharish06.whatsappapi.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import io.github.kpharish06.whatsappapi.entity.ConversationParticipant;
import io.github.kpharish06.whatsappapi.entity.ConversationSettings;

@Repository
public interface ConversationSettingsRepository extends JpaRepository<ConversationSettings, Long> {
	
	 Optional<ConversationSettings> findByUserIdAndConversationId(Long userId, Long conversationId);
	 Optional<ConversationParticipant> findByConversationIdAndUserId(Long conversationId, Long userId);
	 void deleteByUserIdAndConversationId(Long userId, Long conversationId);
	

}
