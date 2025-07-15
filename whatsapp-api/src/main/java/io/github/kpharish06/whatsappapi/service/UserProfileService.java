package io.github.kpharish06.whatsappapi.service;

import org.springframework.web.multipart.MultipartFile;

import io.github.kpharish06.whatsappapi.dto.UserProfileRequest;
import io.github.kpharish06.whatsappapi.dto.UserProfileResponse;

public interface UserProfileService {
	
	UserProfileResponse createProfile(UserProfileRequest dto, MultipartFile profilePic);
	
	UserProfileResponse getProfileByPhoneNumber(String phoneNumber);
	
	//UserProfileResponseDto updateUser(String phoneNumber);
}
