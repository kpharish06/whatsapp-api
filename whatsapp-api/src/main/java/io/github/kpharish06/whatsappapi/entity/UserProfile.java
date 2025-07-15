package io.github.kpharish06.whatsappapi.entity;

import java.time.LocalDateTime;

import org.hibernate.validator.constraints.UniqueElements;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name="user_profile")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserProfile {
	
	@Id @GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	@Column(unique=true, nullable=false, length=10)
	@NotBlank(message="Phone nummber is required")
	@Pattern(regexp="\\d{10}", message="must be 10 digits")
	private String phoneNumber;
	
	@Column(nullable=false)
	@NotBlank(message="cannot be empty")
	private String profileName;
	
	@Column(length=255)
	private String profilePicUrl;
	
	@Column(length=100)
	private String statusMessage;
	
	private LocalDateTime createdAt;
	
	private LocalDateTime updatedAt;
	
    @PrePersist
    protected void onCreate() {
    	this.createdAt = LocalDateTime.now();
	    this.updatedAt = this.createdAt;  
	}

     @PreUpdate
	 protected void onUpdate() {
	    this.updatedAt = LocalDateTime.now();
	 }
	
	
	
	
	
	
	
	
	

}
