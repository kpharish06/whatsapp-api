package io.github.kpharish06.whatsappapi.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserProfileRequest {

    @Pattern(regexp = "\\d{10}", message = "Phone number must be exactly 10 digits")
    private String phoneNumber;

    @NotBlank(message = "Profile name is required")
    private String profileName;

    @Size(max = 255, message = "Avatar URL too long")
    private String profilePicUrl;

    @Size(max = 100, message = "Status message too long")
    private String statusMessage;
    
    public UserProfileRequest(String phoneNumber, String profileName, String statusMessage) {
        this.phoneNumber = phoneNumber;
        this.profileName = profileName;
        this.statusMessage = statusMessage;
    }

}