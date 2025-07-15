package io.github.kpharish06.whatsappapi.dto;

import org.springframework.web.multipart.MultipartFile;

import lombok.Data;

@Data
public class MessageRequest {

    private Long conversationId;       
    private String content;           
    private MultipartFile attachment;
	
}
