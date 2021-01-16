package com.infrastructure.callnotificationsystem.service;

import com.infrastructure.callnotificationsystem.entity.CallHistory;
import com.infrastructure.callnotificationsystem.mapper.CallHistoryMapper;
import com.infrastructure.callnotificationsystem.repository.CallHistoryRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class CallNotificationServiceTest {
    @Mock
    private CallHistoryMapper callHistoryMapper;

    @Mock
    private CallHistoryRepository callHistoryRepository;

    @InjectMocks
    private CallNotificationService callNotificationService;

    @Test
    void whenGetMessageCalledAndCallExistsThenReturnOptionalMessageSuccessfully(){
        final LocalDateTime mockLocalDate = LocalDateTime.of(2020,01,14,21,47);;
        final CallHistory mockCallHistory1 = new CallHistory("05002002020","05001001010", mockLocalDate, 1);
        final CallHistory mockCallHistory2 = new CallHistory("05002002020","05001001011", mockLocalDate, 1);

        List<CallHistory> mockCallHistoryList = new ArrayList<>();
        mockCallHistoryList.add(mockCallHistory1);
        mockCallHistoryList.add(mockCallHistory2);

        given(callHistoryRepository.findByCalledUser("05002002020")).willReturn(mockCallHistoryList);
        given(callHistoryMapper.convertEntitiesToMessage(mockCallHistoryList)).willReturn("Test String");

        Optional<String> optionalMessage = callNotificationService.getCallHistoryMessage("05002002020");

        assertEquals("Test String", optionalMessage.get());
    }

    @Test
    void whenGetMessageCalledAndCallDoesNotExistThenReturnOptionalPresentFalse(){
        List<CallHistory> mockCallHistoryList = new ArrayList<>();

        given(callHistoryRepository.findByCalledUser("05002002020")).willReturn(mockCallHistoryList);

        Optional<String> optionalMessage = callNotificationService.getCallHistoryMessage("05002002020");

        assertFalse(optionalMessage.isPresent());
    }

    @Test
    void whenDeleteCallHistoryCalledThenCallHistoryDeletedSuccessfully(){
        given(callHistoryRepository.existsByCalledUser("05002002020")).willReturn(true);

        callNotificationService.deleteCallHistory("05002002020");

        verify(callHistoryRepository).deleteByCalledUser("05002002020");
    }

    @Test
    void whenDeleteCallHistoryCalledDoesNotExistThenDoNothing(){
        given(callHistoryRepository.existsByCalledUser("05002002020")).willReturn(false);

        callNotificationService.deleteCallHistory("05002002020");

        verify(callHistoryRepository, times(0)).deleteByCalledUser("05002002020");
    }
}
