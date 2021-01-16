package com.infrastructure.callnotificationsystem.service;

import com.infrastructure.callnotificationsystem.dto.DeliveryHistoryDTO;
import com.infrastructure.callnotificationsystem.entity.DeliveryHistory;

public interface DeliveryHistoryServiceInterface {
    DeliveryHistory createDeliveryHistory(DeliveryHistoryDTO deliveryHistoryDTO);
}
