package com.infrastructure.callnotificationsystem.repository;

import com.infrastructure.callnotificationsystem.entity.CallHistory;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.TestPropertySource;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@TestPropertySource(properties = {
        "spring.jpa.hibernate.ddl-auto=update"
})
class CallHistoryRepositoryIntegrationTest {

    @Autowired private CallHistoryRepository callHistoryRepository;

    @Test
    void injectedComponentsShouldNotBeNull(){
        assertNotNull(callHistoryRepository);
    }

    @Test
    void whenFindByCalledUserAndDoesNotExistThenReturnEmptyList(){
        List<CallHistory> callHistoryList = callHistoryRepository.findByCalledUser("05002002020");

        assertTrue(callHistoryList.isEmpty());
    }

    @Test
    void whenCreatedThenFindByCalledUserReturnSuccessfully(){
        callHistoryRepository.save(new CallHistory(
                "05002002020",
                "05001001010",
                LocalDateTime.of(2020,01,14,21,47), 1));

        CallHistory callHistory = callHistoryRepository.findByCalledUser("05002002020").get(0);

        assertEquals("05002002020", callHistory.getCalledUser());
        assertEquals("05001001010", callHistory.getCallerUser());
        assertEquals(LocalDateTime.of(2020,01,14,21,47), callHistory.getLastCallDateTime());
        assertEquals(1, callHistory.getNumberOfCalls());
    }

    @Test
    void whenCreatedSeveralThenFindByCalledUserReturnListSuccessfully(){
        CallHistory callHistory1 = callHistoryRepository.save(new CallHistory(
                "05002002020",
                "05001001010",
                LocalDateTime.of(2020,01,14,21,47), 1));
        CallHistory callHistory2 = callHistoryRepository.save(new CallHistory(
                "05002002020",
                "05001001011",
                LocalDateTime.of(2020,01,14,21,47), 1));


        List<CallHistory> callHistoryList = callHistoryRepository.findByCalledUser("05002002020");

        assertTrue(callHistoryList.contains(callHistory1));
        assertTrue(callHistoryList.contains(callHistory2));
    }

    @Test
    void whenCreatedThenFindByCalledUserAndCallerUserReturnSuccessfully(){
        callHistoryRepository.save(new CallHistory(
                "05002002020",
                "05001001010",
                LocalDateTime.of(2020,01,14,21,47), 1));

        CallHistory callHistory = callHistoryRepository.findByCalledUserAndCallerUser("05002002020", "05001001010").get();

        assertEquals("05002002020", callHistory.getCalledUser());
        assertEquals("05001001010", callHistory.getCallerUser());
        assertEquals(LocalDateTime.of(2020,01,14,21,47), callHistory.getLastCallDateTime());
        assertEquals(1, callHistory.getNumberOfCalls());
    }

    @Test
    void whenUpdatedThenOnlyUpdatedAreaIsChanged(){
        CallHistory callHistory = new CallHistory(
                "05002002020",
                "05001001010",
                LocalDateTime.of(2020,01,14,21,47), 1);
        callHistoryRepository.save(callHistory);

        CallHistory callHistoryWillBeUpdated = callHistoryRepository.findByCalledUser("05002002020").get(0);
        callHistoryWillBeUpdated.setLastCallDateTime(LocalDateTime.of(2020,01,14,22,47));
        callHistoryWillBeUpdated.setNumberOfCalls(2);
        callHistoryRepository.save(callHistoryWillBeUpdated);

        CallHistory callHistoryUpdated = callHistoryRepository.findByCalledUser("05002002020").get(0);
        assertEquals("05002002020", callHistoryUpdated.getCalledUser());
        assertEquals("05001001010", callHistoryUpdated.getCallerUser());
        assertEquals(LocalDateTime.of(2020,01,14,22,47), callHistoryUpdated.getLastCallDateTime());
        assertEquals(2, callHistoryUpdated.getNumberOfCalls());
    }

    @Test
    void whenDeletedByCalledUserAndCallerUserThenFindByCalledUserAndCallerUserThenReturnOptionalPresentFalse(){
        CallHistory callHistory = new CallHistory(
                "05002002020",
                "05001001010",
                LocalDateTime.of(2020,01,14,21,47), 1);
        callHistoryRepository.save(callHistory);

        callHistoryRepository.deleteByCalledUserAndCallerUser("05002002020","05001001010");

        assertFalse(callHistoryRepository.findByCalledUserAndCallerUser("05002002020", "05001001010").isPresent());
    }

    @Test
    void whenExistByCalledUserCalledAndDoesNotExistThenReturnFalse(){
        assertFalse(callHistoryRepository.existsByCalledUser("05001001010"));
    }

    @Test
    void whenExistByCalledUserCalledAndExistsThenReturnTrue(){
        CallHistory callHistory = new CallHistory(
                "05002002020",
                "05001001010",
                LocalDateTime.of(2020,01,14,21,47), 1);
        callHistoryRepository.save(callHistory);

        assertTrue(callHistoryRepository.existsByCalledUser("05002002020"));
    }
}
