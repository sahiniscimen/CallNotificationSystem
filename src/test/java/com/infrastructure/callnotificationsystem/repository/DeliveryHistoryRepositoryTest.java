package com.infrastructure.callnotificationsystem.repository;

import com.infrastructure.callnotificationsystem.entity.DeliveryHistory;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.TestPropertySource;

import java.time.LocalDateTime;
import java.util.List;
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
    void whenFindByCallerUserAndDoesNotExistThenReturnEmptyList(){
        List<DeliveryHistory> deliveryHistoryList = deliveryHistoryRepository.findByCallerUser("05001001010");

        assertTrue(deliveryHistoryList.isEmpty());
    }

    @Test
    void whenCreatedThenFindByCallerUserReturnSuccessfully(){
        deliveryHistoryRepository.save(new DeliveryHistory(
                "05001001010",
                "05002002020",
                LocalDateTime.of(2020,01,14,21,47),
                LocalDateTime.of(2020,01,14,11,47)));

        DeliveryHistory deliveryHistory = deliveryHistoryRepository.findByCallerUser("05001001010").get(0);

        assertEquals("05001001010", deliveryHistory.getCallerUser());
        assertEquals("05002002020", deliveryHistory.getDeliveredUser());
        assertEquals(LocalDateTime.of(2020,01,14,21,47), deliveryHistory.getDeliveryDateTime());
        assertEquals(LocalDateTime.of(2020,01,14,11,47), deliveryHistory.getLastCallDateTime());
    }

    @Test
    void whenCreatedSeveralThenFindByCallerUserReturnListSuccessfully(){
        deliveryHistoryRepository.save(new DeliveryHistory(
                "05001001010",
                "05002002020",
                LocalDateTime.of(2020,01,14,21,47),
                LocalDateTime.of(2020,01,14,11,47)));
        deliveryHistoryRepository.save(new DeliveryHistory(
                "05001001010",
                "05002002021",
                LocalDateTime.of(2020,01,14,21,47),
                LocalDateTime.of(2020,01,14,11,47)));

        DeliveryHistory deliveryHistory1 = deliveryHistoryRepository.findByCallerUser("05001001010").get(0);
        DeliveryHistory deliveryHistory2 = deliveryHistoryRepository.findByCallerUser("05001001010").get(1);

        assertEquals("05001001010", deliveryHistory1.getCallerUser());
        assertEquals("05002002020", deliveryHistory1.getDeliveredUser());
        assertEquals(LocalDateTime.of(2020,01,14,21,47), deliveryHistory1.getDeliveryDateTime());
        assertEquals(LocalDateTime.of(2020,01,14,11,47), deliveryHistory1.getLastCallDateTime());

        assertEquals("05001001010", deliveryHistory2.getCallerUser());
        assertEquals("05002002021", deliveryHistory2.getDeliveredUser());
        assertEquals(LocalDateTime.of(2020,01,14,21,47), deliveryHistory2.getDeliveryDateTime());
        assertEquals(LocalDateTime.of(2020,01,14,11,47), deliveryHistory2.getLastCallDateTime());
    }

    @Test
    void whenDeletedByCallerUserThenFindByCallerUserReturnEmptyList(){
        DeliveryHistory deliveryHistory = new DeliveryHistory(
                "05001001010",
                "05002002020",
                LocalDateTime.of(2020,01,14,21,47),
                LocalDateTime.of(2020,01,14,11,47));
        deliveryHistoryRepository.save(deliveryHistory);

        deliveryHistoryRepository.deleteByCallerUser("05001001010");

        assertTrue(deliveryHistoryRepository.findByCallerUser("05001001010").isEmpty());
    }

    @Test
    void whenExistByCalledUserCalledAndDoesNotExistThenReturnFalse(){
        assertFalse(deliveryHistoryRepository.existsByCallerUser("05001001010"));
    }

    @Test
    void whenExistByCalledUserCalledAndExistsThenReturnTrue(){
        DeliveryHistory deliveryHistory = new DeliveryHistory(
                "05001001010",
                "05002002020",
                LocalDateTime.of(2020,01,14,21,47),
                LocalDateTime.of(2020,01,14,11,47));
        deliveryHistoryRepository.save(deliveryHistory);

        assertTrue(deliveryHistoryRepository.existsByCallerUser("05001001010"));
    }
}
