package io.github.kpharish06.whatsappapi.controller;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import io.github.kpharish06.whatsappapi.dto.UserProfileRequest;
import io.github.kpharish06.whatsappapi.dto.UserProfileResponse;
import io.github.kpharish06.whatsappapi.service.UserProfileService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/profile")
@RequiredArgsConstructor
public class ProfileController {
	
	private final UserProfileService userService;
	
	@Operation(summary = "Create a new user")
	@ApiResponses({
        @ApiResponse(responseCode = "200", description = "Profile created successfully"),
        @ApiResponse(responseCode = "400", description = "Invalid input or picture too large"),
        @ApiResponse(responseCode = "500", description = "Internal server error")
    })
	@PostMapping(consumes= MediaType.MULTIPART_FORM_DATA_VALUE)
	public UserProfileResponse createProfile(
			@RequestParam("phoneNumber") String phoneNumber,
	        @RequestParam("profileName") String profileName,
	        @RequestParam(value = "statusMessage", required = false) String statusMessage,
	        @RequestParam(value = "profilePic", required = false) MultipartFile profilePic) {
		
		UserProfileRequest userRequest = new UserProfileRequest(phoneNumber, profileName, statusMessage);
				return userService.createProfile(userRequest, profilePic);
			}
	
	@GetMapping("/{phoneNumber}")
    public UserProfileResponse getProfile(@PathVariable String phoneNumber) {
        return userService.getProfileByPhoneNumber(phoneNumber);
    }

}
