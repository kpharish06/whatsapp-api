package io.github.kpharish06.whatsappapi.entity;

import java.util.List;

import org.apache.catalina.User;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "chat_settings")
@Getter 
@Setter 
@NoArgsConstructor 
@AllArgsConstructor 
@Builder
public class ConversationSettings {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserProfile user;

    @ManyToOne
    @JoinColumn(name = "conversation_id")
    private Conversation conversation;

    private boolean muted;

    //chat
    @ManyToOne
    @JoinColumn(name = "blocked_user_id")
    private UserProfile blockedUser;

    //group:chat
    @ElementCollection
    @CollectionTable(name = "user_restricted_users", joinColumns = @JoinColumn(name = "setting_id"))
    @Column(name = "restricted_user_id")
    private List<Long> restrictedUserIds;
}
