package io.github.kpharish06.whatsappapi.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import io.github.kpharish06.whatsappapi.entity.ConversationParticipant;

@Repository
public interface ConversationParticipantRepository extends JpaRepository<ConversationParticipant, Long> {
	List<ConversationParticipant> findByConversationId(Long conversationId);

    boolean existsByConversationIdAndUserId(Long conversationId, Long userId);
    
    Optional<ConversationParticipant> findByConversationIdAndUserId(Long conversationId, Long userId);

}
