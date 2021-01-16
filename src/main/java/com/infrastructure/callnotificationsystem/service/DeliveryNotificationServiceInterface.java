package com.infrastructure.callnotificationsystem.service;

import com.infrastructure.callnotificationsystem.entity.DeliveryHistory;

import java.util.List;
import java.util.Optional;

public interface DeliveryNotificationServiceInterface {
    Optional<String> getDeliveryHistoryMessage(String callerUser);
    void deleteDeliveryHistory(String callerUser);
}
