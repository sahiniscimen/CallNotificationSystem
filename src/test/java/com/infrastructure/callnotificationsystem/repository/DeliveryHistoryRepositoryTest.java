package com.infrastructure.callnotificationsystem.repository;

import com.infrastructure.callnotificationsystem.entity.DeliveryHistory;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.TestPropertySource;

import java.time.LocalDateTime;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@TestPropertySource(properties = {
        "spring.jpa.hibernate.ddl-auto=update"
})
class DeliveryHistoryRepositoryTest {

    @Autowired DeliveryHistoryRepository deliveryHistoryRepository;

    @Test
    void injectedComponentsShouldNotBeNull(){ assertNotNull(deliveryHistoryRepository);}

    @Test
    void whenCreatedThenFindByCallerUser(){
        deliveryHistoryRepository.save(new DeliveryHistory(
                "05001001010",
                "05002002020",
                LocalDateTime.of(2020,01,14,21,47),
                LocalDateTime.of(2020,01,14,11,47)));

        DeliveryHistory deliveryHistory = deliveryHistoryRepository.findById("05001001010").get();

        assertEquals("05001001010", deliveryHistory.getCallerUser());
        assertEquals("05002002020", deliveryHistory.getDeliveredUser());
        assertEquals(LocalDateTime.of(2020,01,14,21,47), deliveryHistory.getDeliveryDateTime());
        assertEquals(LocalDateTime.of(2020,01,14,11,47), deliveryHistory.getLastCallDateTime());
    }

    @Test
    void whenDeletedThenFindByIdShouldThrowException(){
        DeliveryHistory deliveryHistory = new DeliveryHistory("05001001010", "05002002020",
                LocalDateTime.of(2020,01,14,21,47), LocalDateTime.of(2020,01,14,11,47));
        deliveryHistoryRepository.save(deliveryHistory);

        deliveryHistoryRepository.delete(deliveryHistory);

        assertThrows(NoSuchElementException.class,() -> deliveryHistoryRepository.findById("05001001010").get());
    }
}
