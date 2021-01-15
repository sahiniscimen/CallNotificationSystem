package com.infrastructure.callnotificationsystem.repository;

import com.infrastructure.callnotificationsystem.entity.CallHistory;
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
class CallHistoryRepositoryTest {

    @Autowired private CallHistoryRepository callHistoryRepository;

    @Test
    void injectedComponentsShouldNotBeNull(){
        assertNotNull(callHistoryRepository);
    }

    @Test
    void whenCreatedThenFindByCalledUser(){
        callHistoryRepository.save(new CallHistory(
                "05002002020",
                "05001001010",
                LocalDateTime.of(2020,01,14,21,47), 1));

        CallHistory callHistory = callHistoryRepository.findById("05002002020").get();

        assertEquals("05002002020", callHistory.getCalledUser());
        assertEquals("05001001010", callHistory.getCallerUser());
        assertEquals(LocalDateTime.of(2020,01,14,21,47), callHistory.getLastCallDateTime());
        assertEquals(1, callHistory.getNumberOfCalls());
    }

    @Test
    void whenCreatedThenFindByCalledUserAndCallerUser(){
        callHistoryRepository.save(new CallHistory(
                "05002002020",
                "05001001010",
                LocalDateTime.of(2020,01,14,21,47), 1));

        CallHistory callHistory = callHistoryRepository.findByCalledUserAndCallerUser("05002002020", "05001001010");

        assertEquals("05002002020", callHistory.getCalledUser());
        assertEquals("05001001010", callHistory.getCallerUser());
        assertEquals(LocalDateTime.of(2020,01,14,21,47), callHistory.getLastCallDateTime());
        assertEquals(1, callHistory.getNumberOfCalls());
    }

    @Test
    void whenUpdatedThenOnlyUpdatedAreaIsChanged(){
        callHistoryRepository.save(new CallHistory(
                "05002002020",
                "05001001010",
                LocalDateTime.of(2020,01,14,21,47), 1));
        callHistoryRepository.save(new CallHistory(
                "05002002020",
                "05001001010",
                LocalDateTime.of(2020,01,14,22,47), 2));

        CallHistory callHistory = callHistoryRepository.findById("05002002020").get();

        assertEquals("05002002020", callHistory.getCalledUser());
        assertEquals("05001001010", callHistory.getCallerUser());
        assertEquals(LocalDateTime.of(2020,01,14,22,47), callHistory.getLastCallDateTime());
        assertEquals(2, callHistory.getNumberOfCalls());
    }

    @Test
    void whenDeletedThenFindByIdShouldThrowException(){
        CallHistory callHistory = new CallHistory(
                "05002002020",
                "05001001010",
                LocalDateTime.of(2020,01,14,21,47), 1);
        callHistoryRepository.save(callHistory);

        callHistoryRepository.delete(callHistory);

        assertThrows(NoSuchElementException.class,() -> callHistoryRepository.findById("05002002020").get());
    }
}
