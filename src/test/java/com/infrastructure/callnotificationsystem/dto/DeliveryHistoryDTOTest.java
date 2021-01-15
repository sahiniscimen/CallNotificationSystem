package com.infrastructure.callnotificationsystem.dto;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

class DeliveryHistoryDTOTest {

    @Test
    void createDeliveryHistoryDTO(){
        DeliveryHistoryDTO deliveryHistoryDTO = new DeliveryHistoryDTO("05001001010", "05002002020");

        assertEquals("05001001010", deliveryHistoryDTO.getCallerUser());
        assertEquals("05002002020", deliveryHistoryDTO.getDeliveredUser());
    }
}
