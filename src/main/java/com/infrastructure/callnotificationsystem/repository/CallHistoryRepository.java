package com.infrastructure.callnotificationsystem.repository;

import com.infrastructure.callnotificationsystem.entity.CallHistory;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CallHistoryRepository extends CrudRepository<CallHistory, Long> {
    boolean existsByCalledUser(String calledUser);
    List<CallHistory> findByCalledUser(String calledUser);
    Optional<CallHistory> findByCalledUserAndCallerUser(String calledUser, String callerUser);
    void deleteByCalledUser(String calledUser);
}
