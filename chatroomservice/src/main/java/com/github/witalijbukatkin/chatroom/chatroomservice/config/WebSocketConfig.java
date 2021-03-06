package com.github.witalijbukatkin.chatroom.chatroomservice.config;

import com.github.witalijbukatkin.chatroom.chatroomservice.socket.interceptor.TopicSubscriptionInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {
    private final TopicSubscriptionInterceptor subscriptionInterceptor;

    @Autowired
    public WebSocketConfig(TopicSubscriptionInterceptor subscriptionInterceptor) {
        this.subscriptionInterceptor = subscriptionInterceptor;
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/websocket").withSockJS();
    }

    @Override
    public void configureClientInboundChannel(ChannelRegistration registration) {
        registration.interceptors(subscriptionInterceptor);
    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        registry.setApplicationDestinationPrefixes("/app")
                .enableStompBrokerRelay("/topic")
                .setRelayHost(System.getenv("RABBITMQ_URL"))
                .setRelayPort(61613)

                .setClientLogin(System.getenv("RABBITMQ_USER"))
                .setSystemLogin(System.getenv("RABBITMQ_USER"))

                .setClientPasscode(System.getenv("RABBITMQ_PASSWORD"))
                .setSystemPasscode(System.getenv("RABBITMQ_PASSWORD"));
    }
}