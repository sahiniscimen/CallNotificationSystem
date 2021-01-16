package com.infrastructure.callnotificationsystem.service;

import com.infrastructure.callnotificationsystem.entity.DeliveryHistory;
import com.infrastructure.callnotificationsystem.mapper.DeliveryHistoryMapper;
import com.infrastructure.callnotificationsystem.repository.DeliveryHistoryRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Log4j2
public class DeliveryNotificationService implements DeliveryNotificationServiceInterface {

    @Autowired
    private DeliveryHistoryMapper deliveryHistoryMapper;

    @Autowired
    private DeliveryHistoryRepository deliveryHistoryRepository;

    @Override
    public Optional<String> getDeliveryHistoryMessage(String callerUser) {
        List<DeliveryHistory> deliveryHistoryList = getDeliveryHistory(callerUser);

        return deliveryHistoryList.isEmpty()?
                Optional.empty():
                Optional.of(deliveryHistoryMapper.convertEntitiesToMessage(deliveryHistoryList));
    }

    @Override
    public void deleteDeliveryHistory(String callerUser) {
        if(deliveryHistoryRepository.existsByCallerUser(callerUser))
            deliveryHistoryRepository.deleteByCallerUser(callerUser);
    }

    private List<DeliveryHistory> getDeliveryHistory(String callerUser){
        return deliveryHistoryRepository.findByCallerUser(callerUser);
    }
}
