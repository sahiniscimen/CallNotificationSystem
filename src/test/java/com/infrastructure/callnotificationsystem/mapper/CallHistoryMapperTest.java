package com.infrastructure.callnotificationsystem.mapper;

import com.infrastructure.callnotificationsystem.dto.CallHistoryDTO;
import com.infrastructure.callnotificationsystem.entity.CallHistory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.test.context.TestPropertySource;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@TestPropertySource(properties = {
        "application.language=eng",
})
class CallHistoryMapperTest {

    @Value("${application.language}")
    private String LANGUAGE;

    private CallHistoryMapper callHistoryMapper;

    @BeforeEach
    void setUp(){
        callHistoryMapper = new CallHistoryMapper();
        callHistoryMapper.LANGUAGE = LANGUAGE;
    }

    @Test
    void whenCreateEntityFromCallHistoryDTOCalledThenEntityShouldMatchWithDTO(){
        CallHistoryDTO callHistoryDTO = new CallHistoryDTO("05002002020","05001001010");

        CallHistory callHistory =
                callHistoryMapper.createEntityFromCallHistoryDTO(callHistoryDTO,
                        LocalDateTime.of(2020,01,14,21,47),1);

        assertEquals("05002002020", callHistory.getCalledUser());
        assertEquals("05001001010", callHistory.getCallerUser());
        assertEquals(1, callHistory.getNumberOfCalls());
        assertEquals(LocalDateTime.of(2020,01,14,21,47), callHistory.getLastCallDateTime());
    }

    @Test
    void whenConvertEntitiesToMessageCalledThenMessageShouldBeCreated(){
        CallHistory callHistory1 = new CallHistory("05002002020","05001001010",
                LocalDateTime.of(2020,01,14,21,47),1);
        CallHistory callHistory2 = new CallHistory("05002002020","05001001011",
                LocalDateTime.of(2020,01,14,22,47),2);
        CallHistory callHistory3 = new CallHistory("05002002020","05001001012",
                LocalDateTime.of(2020,01,14,23,47),3);

        List<CallHistory> callHistoryList = new ArrayList<>();
        callHistoryList.add(callHistory1);
        callHistoryList.add(callHistory2);
        callHistoryList.add(callHistory3);

        callHistoryMapper.LANGUAGE = "eng";
        String message = callHistoryMapper.convertEntitiesToMessage(callHistoryList);
        String  languageDependentPart = "Missed calls: ";

        assertCompareMessageWithLanguageDependentPart(languageDependentPart, message);

        callHistoryMapper.LANGUAGE = "tur";
        message = callHistoryMapper.convertEntitiesToMessage(callHistoryList);
        languageDependentPart = "Sizi arayan numaralar: ";

        assertCompareMessageWithLanguageDependentPart(languageDependentPart, message);
    }

    void assertCompareMessageWithLanguageDependentPart(String languageDependentPart, String message){
        assertEquals(languageDependentPart +
                        "05001001010 " +
                        LocalDateTime.of(2020,01,14,21,47).toString() + " " + 1 + "\n" +
                        "05001001011 " +
                        LocalDateTime.of(2020,01,14,22,47).toString() + " " + 2 + "\n" +
                        "05001001012 " +
                        LocalDateTime.of(2020,01,14,23,47).toString() + " " + 3 + "\n"
                , message);
    }
}
