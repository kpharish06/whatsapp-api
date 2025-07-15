package io.github.kpharish06.whatsappapi.service;



import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import io.github.kpharish06.whatsappapi.dto.AttachmentResponse;
import io.github.kpharish06.whatsappapi.dto.UserProfileRequest;
import io.github.kpharish06.whatsappapi.dto.UserProfileResponse;
import io.github.kpharish06.whatsappapi.entity.AttachmentType;
import io.github.kpharish06.whatsappapi.entity.UserProfile;
import io.github.kpharish06.whatsappapi.repository.UserProfileRepository;
//import jakarta.persistence.criteria.Path;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserProfileServiceImpl implements UserProfileService{

	private final UserProfileRepository userRepository;
	private final String UPLOAD_DIR = "root/picture/";
	private final long MAX_IMAGE_SIZE = 10 * 1024 * 1024;
	private final AttachmentService attachmentService;
	
	@Override
	@Transactional
	public UserProfileResponse createProfile(UserProfileRequest dto, MultipartFile profilePic) {
		
		if (userRepository.existsByPhoneNumber(dto.getPhoneNumber())) {
            throw new RuntimeException("Phone number already registered");
        }
		
		String picPath=null;
		
		if (profilePic != null && !profilePic.isEmpty()) {
	        if (profilePic.getSize() <= MAX_IMAGE_SIZE) {
	        	AttachmentResponse response=attachmentService.storeAttachment(profilePic);
	        	if (response.getType() != AttachmentType.PICTURE) {
	                throw new IllegalArgumentException("Only image are allowed");
	            }

	            picPath = response.getPath();
	            Path directory = Paths.get("/root/picture/"); // ✅

	        }
//	            try {
//	                String filename = UUID.randomUUID() + "_" + profilePic.getOriginalFilename();
//	                Path directory = Paths.get("root/picture/");
//	                Files.createDirectories(directory);
//
//	                Path fullPath = directory.resolve(filename);
//	                Files.copy(profilePic.getInputStream(), fullPath, StandardCopyOption.REPLACE_EXISTING);
//
//	                picPath = fullPath.toString();
//	            } catch (IOException e) {
//	                throw new RuntimeException("Failed");
//	            }
//	        } else {    
//	        	throw new RuntimeException("Profile picture size exceeds 10MB");
//	          }
	    }	
		
		UserProfile user = UserProfile.builder()
	            .phoneNumber(dto.getPhoneNumber())
	            .profileName(dto.getProfileName())
	            .statusMessage(dto.getStatusMessage())
	            .profilePicUrl(picPath)
	            .build();
		 UserProfile saved = userRepository.save(user);
		 
		 return  UserProfileResponse.builder()
		            .id(saved.getId())
		            .phoneNumber(saved.getPhoneNumber())
		            .profileName(saved.getProfileName())
		            .statusMessage(saved.getStatusMessage())
		            .profilePicUrl(saved.getProfilePicUrl())
		            .createdAt(saved.getCreatedAt() != null ? saved.getCreatedAt().toString() : null)
		            .updatedAt(saved.getUpdatedAt() != null ? saved.getUpdatedAt().toString() : null)
		            .build();
	}


	@Override
	public UserProfileResponse getProfileByPhoneNumber(String phoneNumber) {
		 UserProfile user = userRepository.findByPhoneNumber(phoneNumber)
		            .orElseThrow(() -> new RuntimeException("User not found"));

		    return UserProfileResponse.builder()
		            .id(user.getId())
		            .phoneNumber(user.getPhoneNumber())
		            .profileName(user.getProfileName())
		            .statusMessage(user.getStatusMessage())
		            .profilePicUrl(user.getProfilePicUrl())
		            .createdAt(user.getCreatedAt() != null ? user.getCreatedAt().toString() : null)
		            .updatedAt(user.getUpdatedAt() != null ? user.getUpdatedAt().toString() : null)
		            .build();	
	}

}
