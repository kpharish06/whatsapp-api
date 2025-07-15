package io.github.kpharish06.whatsappapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

import io.github.kpharish06.whatsappapi.entity.Conversation;
import io.github.kpharish06.whatsappapi.entity.ConversationParticipant;

@Repository
public interface ConversationRepository extends JpaRepository<Conversation, Long> {

    // Check its DIRECT chat
    @Query("""
        SELECT c FROM Conversation c
        JOIN ConversationParticipant p1 ON p1.conversation = c AND p1.user.id = :user1Id
        JOIN ConversationParticipant p2 ON p2.conversation = c AND p2.user.id = :user2Id
        WHERE c.type = 'DIRECT'
    """)
    Optional<Conversation> findDirectChatBetweenUsers(Long user1Id, Long user2Id);

    @Query("""
    	    SELECT c FROM Conversation c
    	    JOIN ConversationParticipant p ON p.conversation = c
    	    WHERE p.user.id = :userId
    	""")
    Page<Conversation> findByParticipantUserId(@Param("userId") Long userId, Pageable pageable);

}
