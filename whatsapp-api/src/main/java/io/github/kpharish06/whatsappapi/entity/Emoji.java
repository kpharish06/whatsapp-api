package io.github.kpharish06.whatsappapi.entity;

import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "emoji")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Emoji{
	
	@Id
	@GeneratedValue
	private UUID id;
	
	@Column(name = "emojitype", nullable = false, unique = true)
	private String emojiType;  

	@Column(name = "display", nullable = false)
	private String emojiDisplay; 

}
