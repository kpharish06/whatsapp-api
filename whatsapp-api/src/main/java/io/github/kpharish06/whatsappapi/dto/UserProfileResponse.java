package io.github.kpharish06.whatsappapi.dto;

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
public class UserProfileResponse {

    private Long id;
    private String phoneNumber;
    private String profileName;
    private String profilePicUrl;
    private String statusMessage;
    private String createdAt;
    private String updatedAt;
}