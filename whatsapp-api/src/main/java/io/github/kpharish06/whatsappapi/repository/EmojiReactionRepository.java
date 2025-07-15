package io.github.kpharish06.whatsappapi.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import io.github.kpharish06.whatsappapi.entity.EmojiReaction;
import io.github.kpharish06.whatsappapi.entity.Message;
import io.github.kpharish06.whatsappapi.entity.UserProfile;

@Repository
public interface EmojiReactionRepository extends JpaRepository<EmojiReaction, UUID> {
    Optional<EmojiReaction> findByMessageAndUser(Message message, UserProfile user);
    
    List<EmojiReaction> findByMessage(Message message);
}
