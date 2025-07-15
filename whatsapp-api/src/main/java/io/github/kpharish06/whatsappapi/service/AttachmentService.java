package io.github.kpharish06.whatsappapi.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import io.github.kpharish06.whatsappapi.dto.AttachmentResponse;

@Service
public interface AttachmentService {
	AttachmentResponse storeAttachment(MultipartFile file);
	

}
