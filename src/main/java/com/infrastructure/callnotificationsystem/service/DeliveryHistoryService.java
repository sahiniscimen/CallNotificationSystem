package com.infrastructure.callnotificationsystem.service;

import com.infrastructure.callnotificationsystem.dto.DeliveryHistoryDTO;
import com.infrastructure.callnotificationsystem.entity.CallHistory;
import com.infrastructure.callnotificationsystem.entity.DeliveryHistory;
import com.infrastructure.callnotificationsystem.exception.NoSuchCallHistoryException;
import com.infrastructure.callnotificationsystem.mapper.DeliveryHistoryMapper;
import com.infrastructure.callnotificationsystem.repository.CallHistoryRepository;
import com.infrastructure.callnotificationsystem.repository.DeliveryHistoryRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@Transactional
@Log4j2
public class DeliveryHistoryService implements DeliveryHistoryServiceInterface {

    @Autowired
    DeliveryHistoryMapper deliveryHistoryMapper;

    @Autowired
    DeliveryHistoryRepository deliveryHistoryRepository;

    @Autowired
    CallHistoryRepository callHistoryRepository;

    @Override
    public DeliveryHistory createDeliveryHistoryAndDeleteCallHistory(DeliveryHistoryDTO deliveryHistoryDTO) {
        DeliveryHistory deliveryHistory;
        Optional<CallHistory> optionalCallHistory = getCallHistory(deliveryHistoryDTO);

        if (!optionalCallHistory.isPresent())
            throw new NoSuchCallHistoryException("There is not such call history.");
        else{
            deliveryHistory = deliveryHistoryMapper.createEntityFromDeliveryHistoryDTO(
                    deliveryHistoryDTO,
                    LocalDateTime.now(),
                    optionalCallHistory.get().getLastCallDateTime());
            callHistoryRepository.deleteByCalledUserAndCallerUser(deliveryHistory.getDeliveredUser(), deliveryHistoryDTO.getCallerUser());
        }
        return deliveryHistoryRepository.save(deliveryHistory);
    }

    private Optional<CallHistory> getCallHistory(DeliveryHistoryDTO deliveryHistoryDTO) {
        Optional<CallHistory> optionalCallHistory = callHistoryRepository.findByCalledUserAndCallerUser(
                deliveryHistoryDTO.getDeliveredUser(),
                deliveryHistoryDTO.getCallerUser());
        return optionalCallHistory.isPresent() ?
                optionalCallHistory:
                Optional.empty();
    }
}
