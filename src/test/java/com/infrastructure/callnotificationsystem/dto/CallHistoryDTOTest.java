package com.infrastructure.callnotificationsystem.dto;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CallHistoryDTOTest {

    @Test
    void createCallHistoryDTO(){
        CallHistoryDTO callHistoryDTO = new CallHistoryDTO("05002002020","05001001010");

        assertEquals("05002002020", callHistoryDTO.getCalledUser());
        assertEquals("05001001010", callHistoryDTO.getCallerUser());
    }
}
