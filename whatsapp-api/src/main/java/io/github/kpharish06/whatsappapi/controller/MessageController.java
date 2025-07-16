package io.github.kpharish06.whatsappapi.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
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
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@Tag(name = "Messages", description = "Endpoint for sending and recieving messages")
@RestController
@RequestMapping("/api/messages")
@RequiredArgsConstructor
public class MessageController {
	
	private final MessageService messageService;
	private static final Logger log = LoggerFactory.getLogger(MessageController.class);

	@Operation(summary = "Send a message, Attachment(Optional) ")
	@ApiResponses(value = {
		    @ApiResponse(responseCode = "200", description = "Message has been sent"),
		    @ApiResponse(responseCode = "400", description = "Invalid input"),
		    @ApiResponse(responseCode = "400", description = "Invalid input")
		})
	@PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<MessageResponse> sendMessage(
	        @Parameter(description = "sender id") 
			@RequestParam("senderId") Long senderId,
	        @Parameter(description = "conversations  will be created after the user creates a conversation") 
			@RequestParam("conversationId") Long conversationId,
	        @Parameter(description = "text content") 
			@RequestParam("content") String content,
	        @Parameter(description = "img/video/audio (Optional) ,<10Mb") 

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
	@Operation(summary = "Retrieve the messages from conversation using Pagination")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Messages retrieved successfully"),
        @ApiResponse(responseCode = "400", description = "invalid parameters"),
        @ApiResponse(responseCode = "500", description = "Internal server error")
    })
	@GetMapping
	public ResponseEntity<Page<MessageResponse>> getMessages(
			@RequestParam Long conversationId,
	        @RequestHeader("X-User-Id") Long userId,
	        @ParameterObject Pageable pageable) {
	    Page<MessageResponse> page = messageService.getMessages(conversationId, pageable, userId);
	    return ResponseEntity.ok(page);
	}

}
