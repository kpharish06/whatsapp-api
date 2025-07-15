package io.github.kpharish06.whatsappapi.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;

import io.github.kpharish06.whatsappapi.dto.MessageRequest;
import io.github.kpharish06.whatsappapi.dto.MessageResponse;
import io.github.kpharish06.whatsappapi.service.MessageService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/messages")
@RequiredArgsConstructor
public class MessageController {
	
	private final MessageService messageService;
	private static final Logger log = LoggerFactory.getLogger(MessageController.class);

	@PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<MessageResponse> sendMessage(
	        @RequestParam("senderId") Long senderId,
	        @RequestParam("conversationId") Long conversationId,
	        @RequestParam("content") String content,
	        @RequestParam(value = "attachment", required = false) MultipartFile attachment) {

	    log.info("Sending message from senderId={} to conversationId={} with content={}", senderId, conversationId, content);

	    MessageRequest request = new MessageRequest();
	    request.setConversationId(conversationId);
	    request.setContent(content);
	    request.setAttachment(attachment);
	    log.info("Sending message from senderId={} to conversationId={} with content={}", senderId, conversationId, content);
	    MessageResponse response = messageService.sendMessage(request, senderId);
	   
	    return ResponseEntity.ok(response);
	}
	@GetMapping
	public ResponseEntity<Page<MessageResponse>> getMessages(
			@RequestParam Long conversationId,
	        @RequestHeader("X-User-Id") Long userId,
	        @ParameterObject Pageable pageable) {
	    Page<MessageResponse> page = messageService.getMessages(conversationId, pageable, userId);
	    return ResponseEntity.ok(page);
	}

}
