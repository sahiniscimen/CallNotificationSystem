package com.infrastructure.callnotificationsystem.service;

import com.infrastructure.callnotificationsystem.dto.DeliveryHistoryDTO;
import com.infrastructure.callnotificationsystem.entity.CallHistory;
import com.infrastructure.callnotificationsystem.entity.DeliveryHistory;
import com.infrastructure.callnotificationsystem.exception.NoSuchCallHistoryException;
import com.infrastructure.callnotificationsystem.mapper.DeliveryHistoryMapper;
import com.infrastructure.callnotificationsystem.repository.CallHistoryRepository;
import com.infrastructure.callnotificationsystem.repository.DeliveryHistoryRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class DeliveryHistoryServiceTest {

    @Mock
    private DeliveryHistoryMapper deliveryHistoryMapper;

    @Mock
    private DeliveryHistoryRepository deliveryHistoryRepository;

    @Mock
    private CallHistoryRepository callHistoryRepository;

    @InjectMocks
    private DeliveryHistoryService deliveryHistoryService;

    @Test
    void whenCreateCalledAndCallHistoryExistsThenCreateAndDeleteSuccessfully(){
        final LocalDateTime mockLocalDate = LocalDateTime.of(2020,01,14,21,47);
        final CallHistory mockCallHistory = new CallHistory("05002002020","05001001010", mockLocalDate, 1);
        final DeliveryHistory mockDeliveryHistory = new DeliveryHistory("05001001010","05002002020", mockLocalDate,mockLocalDate);
        final DeliveryHistoryDTO mockDeliveryHistoryDTO = new DeliveryHistoryDTO("05001001010","05002002020");

        given(callHistoryRepository.findByCalledUserAndCallerUser("05002002020","05001001010"))
                .willReturn(Optional.of(mockCallHistory));
        given(deliveryHistoryMapper.createEntityFromDeliveryHistoryDTO(
                eq(mockDeliveryHistoryDTO),
                any(LocalDateTime.class),
                eq(mockLocalDate))).willReturn(mockDeliveryHistory);
        given(deliveryHistoryRepository.save(any())).willAnswer(invocationOnMock -> invocationOnMock.getArguments()[0]);

        DeliveryHistory deliveryHistory = deliveryHistoryService.createDeliveryHistoryAndDeleteCallHistory(mockDeliveryHistoryDTO);

        verify(callHistoryRepository, times(1)).deleteByCalledUserAndCallerUser("05002002020","05001001010");
        assertEquals("05001001010", deliveryHistory.getCallerUser());
        assertEquals("05002002020", deliveryHistory.getDeliveredUser());
        assertEquals(mockLocalDate, deliveryHistory.getDeliveryDateTime());
        assertEquals(mockLocalDate, deliveryHistory.getLastCallDateTime());
    }

    @Test
    void whenCreateCalledAndCallHistoryDoesNotExistThenThrowException(){
        final DeliveryHistoryDTO mockDeliveryHistoryDTO = new DeliveryHistoryDTO("05001001010","05002002020");
        given(callHistoryRepository.findByCalledUserAndCallerUser("05002002020","05001001010"))
                .willReturn(Optional.empty());

        assertThrows(NoSuchCallHistoryException.class, () -> deliveryHistoryService.createDeliveryHistoryAndDeleteCallHistory(mockDeliveryHistoryDTO));
    }
}
