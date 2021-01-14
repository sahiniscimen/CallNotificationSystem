package com.infrastructure.callnotificationsystem.entity;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDateTime;

class CallHistoryTest {

    @Test
    void createCallHistory(){
        CallHistory callHistory = new CallHistory("05002002020","05001001010", LocalDateTime.of(2020,01,14,21,47));

        assertEquals("05002002020", callHistory.getCalledUser());
        assertEquals("05001001010", callHistory.getCallerUser());
        assertEquals(LocalDateTime.of(2020,01,14,21,47), callHistory.getCallDateTime());
    }
}
