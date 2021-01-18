package com.infrastructure.callnotificationsystem.configuration;

import com.infrastructure.callnotificationsystem.dto.UserDTO;
import com.infrastructure.callnotificationsystem.service.UserChannelService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

import java.util.Optional;

@Configuration
@EnableWebSocketMessageBroker
@Log4j2
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    @Autowired
    private UserChannelService userChannelService;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        config.enableSimpleBroker("/");
        config.setApplicationDestinationPrefixes("/app");
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/notification");
        registry.addEndpoint("/notification")
                .withSockJS();
    }

    @Override
    public void configureClientInboundChannel(ChannelRegistration registration) {
        registration.interceptors(new ChannelInterceptor(){
            @Override
            public Message<?> preSend(Message<?> message, MessageChannel channel) {
                StompHeaderAccessor headers = StompHeaderAccessor.wrap(message);

                if(headers.getCommand().equals(StompCommand.SUBSCRIBE)) {
                    String phoneNumber = headers.getNativeHeader("PhoneNumber").get(0);
                    String password = headers.getNativeHeader("Password").get(0);
                    String phoneDestination = headers.getDestination().toString();

                    log.info(phoneNumber + " is trying to subscribe.");

                    Optional<UserDTO> optionalUserDTO = userChannelService.getUserByPhoneNumber(phoneNumber);

                    if (!optionalUserDTO.isPresent())
                        throw new IllegalArgumentException("PhoneNumber is not registered.");
                    if (!bCryptPasswordEncoder.matches(password, optionalUserDTO.get().getPassword()))
                        throw new IllegalArgumentException("Password is invalid.");
                }
                return message;
            }
        });
    }
}
