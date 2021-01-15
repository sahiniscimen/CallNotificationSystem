package com.infrastructure.callnotificationsystem.entity;

import com.infrastructure.callnotificationsystem.dto.DeliveryHistoryDTO;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDateTime;

class DeliveryHistoryTest {

    @Test
    void createDeliveryHistory(){
        DeliveryHistory deliveryHistory = new DeliveryHistory("05001001010", "05002002020",
                LocalDateTime.of(2020,01,14,21,47), LocalDateTime.of(2020,01,14,11,47));

        assertEquals("05001001010", deliveryHistory.getCallerUser());
        assertEquals("05002002020", deliveryHistory.getDeliveredUser());
        assertEquals(LocalDateTime.of(2020,01,14,21,47), deliveryHistory.getDeliveryDateTime());
        assertEquals(LocalDateTime.of(2020,01,14,11,47), deliveryHistory.getLastCallDateTime());
    }
}
