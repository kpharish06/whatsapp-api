package io.github.kpharish06.whatsappapi.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

import io.github.kpharish06.whatsappapi.dto.SeenRequest;
import io.github.kpharish06.whatsappapi.service.MessageService;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {
	
	 @Override
	 public void registerStompEndpoints(StompEndpointRegistry registry) {
		 registry.addEndpoint("/ws").setAllowedOriginPatterns("*")
		 .withSockJS(); 
	 }
	 
	 @Override
	 public void configureMessageBroker(MessageBrokerRegistry config) {      
		 config.enableSimpleBroker("/topic"); // Broker will broadcast messages on /topic/...
	     config.setApplicationDestinationPrefixes("/app"); // Clients will send messages to /app/...
	 }
}
