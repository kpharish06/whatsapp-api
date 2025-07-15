package io.github.kpharish06.whatsappapi.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import io.github.kpharish06.whatsappapi.entity.UserProfile;

@Repository
public interface UserProfileRepository extends JpaRepository<UserProfile, Long> {
	Optional<UserProfile> findByPhoneNumber(String phoneNumber);

	boolean existsByPhoneNumber(String phoneNumber);
	
	
}
