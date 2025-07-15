package io.github.kpharish06.whatsappapi.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import io.github.kpharish06.whatsappapi.entity.Message;
import io.github.kpharish06.whatsappapi.entity.MessageStatus;
import io.github.kpharish06.whatsappapi.entity.UserProfile;

@Repository
public interface MessageStatusRepository extends JpaRepository<MessageStatus, UUID> {
	Optional<MessageStatus> findByMessageAndUser(Message message, UserProfile user);
	
	List<MessageStatus> findByUser(UserProfile user);

	List<MessageStatus> findByMessage(Message message);
	
    Optional<MessageStatus> findByMessageIdAndUserId(UUID messageId, Long userId);

}
