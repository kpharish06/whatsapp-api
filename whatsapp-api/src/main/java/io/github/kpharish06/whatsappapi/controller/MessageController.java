package io.github.kpharish06.whatsappapi.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.github.kpharish06.whatsappapi.dto.MessageRequest;
import io.github.kpharish06.whatsappapi.dto.MessageResponse;
import io.github.kpharish06.whatsappapi.service.MessageService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/messages")
@RequiredArgsConstructor
public class MessageController {
	
	private final MessageService messageService;
	
	@PostMapping
	public ResponseEntity<MessageResponse> sendMessage(
            @ModelAttribute MessageRequest request,
            @RequestParam Long senderId) {

        MessageResponse response = messageService.sendMessage(request, senderId);
        return ResponseEntity.ok(response);
    }
}
