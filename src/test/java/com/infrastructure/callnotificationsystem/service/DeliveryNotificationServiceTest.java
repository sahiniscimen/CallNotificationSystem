package com.infrastructure.callnotificationsystem.service;

import com.infrastructure.callnotificationsystem.entity.DeliveryHistory;
import com.infrastructure.callnotificationsystem.mapper.DeliveryHistoryMapper;
import com.infrastructure.callnotificationsystem.repository.DeliveryHistoryRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class DeliveryNotificationServiceTest {

    @Mock
    private DeliveryHistoryMapper deliveryHistoryMapper;

    @Mock
    private DeliveryHistoryRepository deliveryHistoryRepository;

    @InjectMocks
    private DeliveryNotificationService deliveryNotificationService;

    @Test
    void whenGetMessageCalledAndCallExistsThenReturnOptionalMessageSuccessfully(){
        final LocalDateTime mockLocalDate = LocalDateTime.of(2020,01,14,21,47);;
        DeliveryHistory mockDeliveryHistory1 = new DeliveryHistory("05001001010", "05002002020", mockLocalDate, mockLocalDate);
        DeliveryHistory mockDeliveryHistory2 = new DeliveryHistory("05001001010", "05002002021", mockLocalDate, mockLocalDate);

        List<DeliveryHistory> mockDeliveryHistoryList = new ArrayList<>();
        mockDeliveryHistoryList.add(mockDeliveryHistory1);
        mockDeliveryHistoryList.add(mockDeliveryHistory2);

        given(deliveryHistoryRepository.findByCallerUser("05001001010")).willReturn(mockDeliveryHistoryList);
        given(deliveryHistoryMapper.convertEntitiesToMessage(mockDeliveryHistoryList)).willReturn("Test String");

        Optional<String> message = deliveryNotificationService.getDeliveryHistoryMessage("05001001010");

        assertEquals("Test String", message.get());
    }

    @Test
    void whenGetMessageCalledAndCallDoesNotExistThenReturnOptionalPresentFalse(){
        List<DeliveryHistory> mockDeliveryHistoryList = new ArrayList<>();

        given(deliveryHistoryRepository.findByCallerUser("05001001010")).willReturn(mockDeliveryHistoryList);

        Optional<String> message = deliveryNotificationService.getDeliveryHistoryMessage("05001001010");

        assertFalse(message.isPresent());
    }

    @Test
    void whenDeleteDeliveryHistoryCalledThenCallHistoryDeletedSuccessfully(){
        given(deliveryHistoryRepository.existsByCallerUser("05001001010")).willReturn(true);

        deliveryNotificationService.deleteDeliveryHistory("05001001010");

        verify(deliveryHistoryRepository, times(1)).deleteByCallerUser("05001001010");
    }

    @Test
    void whenDeleteDeliveryHistoryCalledDoesNotExistThenDoNothing(){
        given(deliveryHistoryRepository.existsByCallerUser("05001001010")).willReturn(false);

        deliveryNotificationService.deleteDeliveryHistory("05001001010");

        verify(deliveryHistoryRepository, times(0)).deleteByCallerUser("05001001010");
    }
}
