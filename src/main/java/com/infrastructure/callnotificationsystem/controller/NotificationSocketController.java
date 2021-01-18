package com.infrastructure.callnotificationsystem.controller;

import com.infrastructure.callnotificationsystem.service.CallNotificationService;
import com.infrastructure.callnotificationsystem.service.DeliveryNotificationService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.socket.messaging.SessionSubscribeEvent;

import java.util.Optional;
import java.util.stream.Stream;

@Controller
@Log4j2
public class NotificationSocketController {
    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;

    @Autowired
    private CallNotificationService callNotificationService;

    @Autowired
    private DeliveryNotificationService deliveryNotificationService;

    @EventListener
    public void notificationHandlerOnSubscribeEvent(SessionSubscribeEvent event) {
        SimpMessageHeaderAccessor headers = SimpMessageHeaderAccessor.wrap(event.getMessage());
        String phoneNumber = headers.getNativeHeader("PhoneNumber").get(0);
        String phoneDestination = headers.getDestination();

        Optional<String> callHistoryMessage = callNotificationService.getCallHistoryMessage(phoneNumber);
        Optional<String> deliveryHistoryMessage = deliveryNotificationService.getDeliveryHistoryMessage(phoneNumber);
        Optional<String> notificationMessage = getNotificationMessage(callHistoryMessage, deliveryHistoryMessage);

        if(notificationMessage.isPresent())
            sendNotification(phoneDestination, notificationMessage.get());
        if(deliveryHistoryMessage.isPresent())
            deleteDeliveryHistory(phoneNumber);
    }

    private Optional<String> getNotificationMessage(Optional<String> callHistoryMessage, Optional<String> deliveryHistoryMessage){
        return Stream.of(callHistoryMessage,
                        deliveryHistoryMessage)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .reduce((m1, m2) -> m1 + " \n" + m2);
    }

    private void sendNotification(String phoneDestination, String notificationMessage){
        log.info("Notification is sent to : " + phoneDestination);
        simpMessagingTemplate.convertAndSend(phoneDestination, notificationMessage);
    }

    private void deleteDeliveryHistory(String callerUser){
        deliveryNotificationService.deleteDeliveryHistory(callerUser);
        log.info(callerUser + " : DeliveryHistory is deleted.");
    }
}
