package com.infrastructure.callnotificationsystem.service;

import com.infrastructure.callnotificationsystem.dto.CallHistoryDTO;
import com.infrastructure.callnotificationsystem.entity.CallHistory;
import com.infrastructure.callnotificationsystem.mapper.CallHistoryMapper;
import com.infrastructure.callnotificationsystem.repository.CallHistoryRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class CallHistoryServiceTest {

    @Mock
    private CallHistoryMapper callHistoryMapper;

    @Mock
    private CallHistoryRepository callHistoryRepository;

    @InjectMocks
    private CallHistoryService callHistoryService;

    @Test
    void whenNewCallHistoryArrivedThenShouldBeCreatedSuccessfully(){
        final LocalDateTime mockLocalDate = LocalDateTime.of(2020,01,14,21,47);
        final CallHistory mockCallHistory = new CallHistory("05002002020","05001001010", mockLocalDate, 1);
        final CallHistoryDTO mockCallHistoryDTO = new CallHistoryDTO("05002002020","05001001010");
        given(callHistoryRepository.findByCalledUserAndCallerUser(mockCallHistoryDTO.getCalledUser(), mockCallHistoryDTO.getCallerUser()))
                .willReturn(Optional.empty());
        given(callHistoryMapper.createEntityFromCallHistoryDTO(eq(mockCallHistoryDTO), any(LocalDateTime.class), eq(1)))
                .willReturn(mockCallHistory);
        given(callHistoryRepository.save(mockCallHistory))
                .willReturn(mockCallHistory);

        CallHistory savedCallHistory = callHistoryService.createOrUpdateCallHistory(mockCallHistoryDTO);

        assertEquals(mockCallHistoryDTO.getCalledUser(), savedCallHistory.getCalledUser());
        assertEquals(mockCallHistoryDTO.getCallerUser(), savedCallHistory.getCallerUser());
        assertEquals(mockLocalDate , savedCallHistory.getLastCallDateTime());
        assertEquals(1, savedCallHistory.getNumberOfCalls());
    }

    @Test
    void whenCallHistoryAlreadyExistsArrivedThenShouldBeUpdatedSuccessfully(){
        final CallHistoryDTO mockCallHistoryDTO = new CallHistoryDTO("05002002020","05001001010");
        final LocalDateTime mockLocalDate = LocalDateTime.of(2020,01,14,21,47);;
        final CallHistory mockCallHistory = new CallHistory("05002002020","05001001010", mockLocalDate, 1);

        given(callHistoryRepository.findByCalledUserAndCallerUser(mockCallHistory.getCalledUser(), mockCallHistory.getCallerUser()))
                .willReturn(Optional.of(mockCallHistory));
        given(callHistoryRepository.save(any())).willAnswer(invocationOnMock -> invocationOnMock.getArguments()[0]);


        CallHistory updatedCallHistory = callHistoryService.createOrUpdateCallHistory(mockCallHistoryDTO);

        assertEquals(mockCallHistoryDTO.getCalledUser(), updatedCallHistory.getCalledUser());
        assertEquals(mockCallHistoryDTO.getCallerUser(), updatedCallHistory.getCallerUser());
        assertEquals(LocalDateTime.class, updatedCallHistory.getLastCallDateTime().getClass());
        assertEquals(2, updatedCallHistory.getNumberOfCalls());
    }
}
