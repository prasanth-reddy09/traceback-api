package com.traceback.api.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker // <-- THIS is the magic line that creates the SimpMessagingTemplate!
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        // This is the URL the Next.js frontend will use to establish the tunnel
        registry.addEndpoint("/ws")
                .setAllowedOriginPatterns("https://traceback-frontend-one.vercel.app", "http://localhost:3000") // Allow our frontend to connect
                .withSockJS(); // Fallback for older browsers
    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        // The server will "broadcast" messages to channels starting with "/topic"
        registry.enableSimpleBroker("/topic");
        
        // When the frontend sends a message TO the server, it must start with "/app"
        registry.setApplicationDestinationPrefixes("/app");
    }
}