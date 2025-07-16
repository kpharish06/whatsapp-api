package io.github.kpharish06.whatsappapi.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.github.kpharish06.whatsappapi.dto.ConversationRequest;
import io.github.kpharish06.whatsappapi.dto.ConversationResponse;
import io.github.kpharish06.whatsappapi.service.ConversationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/conversations")
@RequiredArgsConstructor
@Tag(name = "Endpoints for Conversations")
public class ConversationController {
	private final ConversationService conversationService;
	
	@Operation(summary = "Create a new conversation (direct or group)")
	@ApiResponses({
        @ApiResponse(responseCode = "201", description = "Conversation created successfully"),
        @ApiResponse(responseCode = "400", description = "Invalid participants list"),
        @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PostMapping
    public ResponseEntity<ConversationResponse> createConversation(
            @RequestBody @Valid ConversationRequest request,
            @RequestHeader("X-User-Id") Long currentUserId
            )
	{
        ConversationResponse response = conversationService.createConversation(request, currentUserId);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
	
	
	@GetMapping
    @Operation(summary = "Listing all conversations for the current user")
	@ApiResponses({
        @ApiResponse(responseCode = "200", description = "Conversations recieved"),
        @ApiResponse(responseCode = "400", description = "Missing or invalid parameters"),
        @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<Page<ConversationResponse>> getAllConversations(
            @RequestHeader("X-User-Id") Long currentUserId,
            @ParameterObject Pageable pageable
    ) {
        Page<ConversationResponse> response = conversationService.getConversationsForUser(currentUserId, pageable);
        return ResponseEntity.ok(response);
    }
    

}
