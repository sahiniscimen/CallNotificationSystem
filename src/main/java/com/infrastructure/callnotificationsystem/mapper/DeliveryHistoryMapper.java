package com.infrastructure.callnotificationsystem.mapper;

import com.infrastructure.callnotificationsystem.dto.DeliveryHistoryDTO;
import com.infrastructure.callnotificationsystem.entity.DeliveryHistory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class DeliveryHistoryMapper {

    @Value( "${application.language}" )
    public String LANGUAGE;

    public DeliveryHistory createEntityFromDeliveryHistoryDTO(DeliveryHistoryDTO deliveryHistoryDTO, LocalDateTime deliveryDateTime, LocalDateTime lastCallDateTime){
        return new DeliveryHistory(deliveryHistoryDTO.getCallerUser(), deliveryHistoryDTO.getDeliveredUser(), deliveryDateTime, lastCallDateTime);
    }

    public String convertEntitiesToMessage(List<DeliveryHistory> deliveryHistoryList){
        return LANGUAGE.equals("eng") ?
                stringifyDeliveryHistoryListEnglish(deliveryHistoryList):
                stringifyDeliveryHistoryListTurkish(deliveryHistoryList);
    }

    private String stringifyDeliveryHistoryListEnglish(List<DeliveryHistory> deliveryHistoryList){
        return deliveryHistoryList.stream().map(deliveryHistory -> "The number you called " +
                deliveryHistory.getDeliveredUser() + " at " + deliveryHistory.getLastCallDateTime().toString() +
                        ", became available at " + deliveryHistory.getDeliveryDateTime().toString() + "\n"
        ).collect(Collectors.joining());
    }

    private String stringifyDeliveryHistoryListTurkish(List<DeliveryHistory> deliveryHistoryList){
        return deliveryHistoryList.stream().map(deliveryHistory -> deliveryHistory.getLastCallDateTime().toString() +
                " tarihinde aradığınız numara " + deliveryHistory.getDeliveredUser() + ", " +
                deliveryHistory.getDeliveryDateTime().toString() + " tarihinde ulaşılabilir duruma gelmiştir.\n"
        ).collect(Collectors.joining());
    }

}
