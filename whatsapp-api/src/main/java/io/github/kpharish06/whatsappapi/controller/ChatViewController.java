package io.github.kpharish06.whatsappapi.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Conversations", description = "UI chat interface ")
@Controller
public class ChatViewController {

    @GetMapping("/chat")
    public String showChatPage() {
        return "chat"; 
    }
}
