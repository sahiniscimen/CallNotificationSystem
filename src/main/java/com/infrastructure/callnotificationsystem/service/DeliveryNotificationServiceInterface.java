package com.infrastructure.callnotificationsystem.service;

import java.util.Optional;

public interface DeliveryNotificationServiceInterface {
    Optional<String> getDeliveryHistoryMessage(String callerUser);
    void deleteDeliveryHistory(String callerUser);
}
