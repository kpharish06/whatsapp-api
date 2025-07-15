package io.github.kpharish06.whatsappapi.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import io.github.kpharish06.whatsappapi.entity.Emoji;
import io.github.kpharish06.whatsappapi.entity.EmojiReaction;
import io.github.kpharish06.whatsappapi.entity.Message;

@Repository
public interface EmojiRepository extends JpaRepository<Emoji, UUID> {
    Optional<Emoji> findByEmojiType(String emojiType);
}

