package com.infrastructure.callnotificationsystem.mapper;

import com.infrastructure.callnotificationsystem.dto.DeliveryHistoryDTO;
import com.infrastructure.callnotificationsystem.entity.DeliveryHistory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class DeliveryHistoryMapperTest {

    DeliveryHistoryMapper deliveryHistoryMapper;

    @BeforeEach
    void setUp() {
        deliveryHistoryMapper = new DeliveryHistoryMapper();
    }

    @Test
    void whenCreateEntityFromCallHistoryDTOCalledThenEntityShouldMatchWithDTO(){
        DeliveryHistoryDTO deliveryHistoryDTO = new DeliveryHistoryDTO("05001001010", "05002002020");

        DeliveryHistory deliveryHistory =
                deliveryHistoryMapper.createEntityFromDeliveryHistoryDTO(deliveryHistoryDTO,
                LocalDateTime.of(2020,01,14,21,47),
                LocalDateTime.of(2020,01,14,11,47));

        assertEquals("05001001010", deliveryHistory.getCallerUser());
        assertEquals("05002002020", deliveryHistory.getDeliveredUser());
        assertEquals(LocalDateTime.of(2020,01,14,21,47), deliveryHistory.getDeliveryDateTime());
        assertEquals(LocalDateTime.of(2020,01,14,11,47), deliveryHistory.getLastCallDateTime());
    }

    @Test
    void whenConvertEntitiesToMessageCalledThenMessageShouldBeCreated(){
        DeliveryHistory deliveryHistory1 = new DeliveryHistory("05001001010", "05002002020",
                LocalDateTime.of(2020,01,14,21,47), LocalDateTime.of(2020,01,14,11,47));
        DeliveryHistory deliveryHistory2 = new DeliveryHistory("05001001010", "05002002021",
                LocalDateTime.of(2020,01,14,22,47), LocalDateTime.of(2020,01,14,11,47));
        DeliveryHistory deliveryHistory3 = new DeliveryHistory("05001001010", "05002002022",
                LocalDateTime.of(2020,01,14,23,47), LocalDateTime.of(2020,01,14,11,47));

        List<DeliveryHistory> deliveryHistoryList = new ArrayList<>();
        deliveryHistoryList.add(deliveryHistory1);
        deliveryHistoryList.add(deliveryHistory2);
        deliveryHistoryList.add(deliveryHistory3);

        String message = deliveryHistoryMapper.convertEntitiesToMessage(deliveryHistoryList);

        if(deliveryHistoryMapper.LANGUAGE.equals("eng"))
            assertEquals(
                "The number you called 05002002020 at " + deliveryHistory1.getLastCallDateTime().toString() +
                ", became available at " + deliveryHistory1.getDeliveryDateTime().toString() + "/n" +
                "The number you called 05002002021 at " + deliveryHistory2.getLastCallDateTime().toString() +
                ", became available at " + deliveryHistory2.getDeliveryDateTime().toString() + "/n" +
                "The number you called 05002002022 at " + deliveryHistory3.getLastCallDateTime().toString() +
                ", became available at " + deliveryHistory3.getDeliveryDateTime().toString() + "/n",
                message);
        else
            assertEquals(
                deliveryHistory1.getLastCallDateTime().toString() + " tarihinde aradığınız numara 05002002020, " +
                        deliveryHistory1.getDeliveryDateTime().toString() + " tarihinde ulaşılabilir duruma gelmiştir./n" +
                        deliveryHistory2.getLastCallDateTime().toString() + " tarihinde aradığınız numara 05002002021, " +
                        deliveryHistory2.getDeliveryDateTime().toString() + " tarihinde ulaşılabilir duruma gelmiştir./n" +
                        deliveryHistory3.getLastCallDateTime().toString() + " tarihinde aradığınız numara 05002002022, " +
                        deliveryHistory3.getDeliveryDateTime().toString() + " tarihinde ulaşılabilir duruma gelmiştir./n",
                message);
    }
}
