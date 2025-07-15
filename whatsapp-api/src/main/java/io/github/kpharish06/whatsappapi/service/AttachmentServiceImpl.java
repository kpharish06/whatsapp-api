package io.github.kpharish06.whatsappapi.service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import io.github.kpharish06.whatsappapi.dto.AttachmentResponse;
import io.github.kpharish06.whatsappapi.entity.AttachmentType;

@Service
public class AttachmentServiceImpl implements AttachmentService {


	@Override
	public AttachmentResponse storeAttachment(MultipartFile file) {
		// TODO Auto-generated method stub
		validateSize(file);
		
		AttachmentType type=findFileType(file);
		String path=saveToDisk(file,type);
		
		return new AttachmentResponse(path,type); 
	}

	private String saveToDisk(MultipartFile file,AttachmentType type) {
	    try {
	    	 String basePath = type == AttachmentType.PICTURE ? "/root/picture/" : "/root/video/";
	    	 File directory = new File(basePath);
	         if (!directory.exists()) {
	            directory.mkdirs(); 
	         }

	         String originalFilename = file.getOriginalFilename();
	         String extension = "";

	         if (originalFilename != null && originalFilename.contains(".")) {
	            extension = originalFilename.substring(originalFilename.lastIndexOf("."));
	         }

	         String uniqueFileName = UUID.randomUUID().toString() + extension;

	         // Full path to save
	         Path path = Paths.get(basePath + uniqueFileName);
	         Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);

	         return path.toString(); // Return path to store in DB

	    } catch (IOException e) {
	        throw new RuntimeException("Failed to save file", e);
	    }
	}


	private AttachmentType findFileType(MultipartFile file) {
	    String contentType = file.getContentType();

	    if (contentType == null) {
	        throw new IllegalArgumentException("Unable to attach");
	    }

	    if (contentType.startsWith("image/")) {
	        return AttachmentType.PICTURE;
	    } else if (contentType.startsWith("video/")) {
	        return AttachmentType.VIDEO;
	    } else {
	        throw new IllegalArgumentException("Unsupported file type: " + contentType);
	    }
	}


	private void validateSize(MultipartFile file) {
	    long MAX_SIZE = 10 * 1024 * 1024; // 10 MB
	    if (file.getSize() > MAX_SIZE) {
	        throw new IllegalArgumentException("Attachment exceeds 10MB size limit.");
	    }
	}

}
