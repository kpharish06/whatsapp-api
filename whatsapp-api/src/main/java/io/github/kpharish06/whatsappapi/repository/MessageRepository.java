package io.github.kpharish06.whatsappapi.repository;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import io.github.kpharish06.whatsappapi.entity.Message;

@Repository
public interface MessageRepository extends JpaRepository<Message, UUID> {

	 Page<Message> findByConversationIdOrderByTimestampDesc(Long conversationId, Pageable pageable);
}
