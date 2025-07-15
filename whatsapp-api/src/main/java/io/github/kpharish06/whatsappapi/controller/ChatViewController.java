package io.github.kpharish06.whatsappapi.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Controller
public class ChatViewController {

    @GetMapping("/chat")
    public String showChatPage() {
        return "chat"; // this resolves to /WEB-INF/views/chat.jsp
    }
}
