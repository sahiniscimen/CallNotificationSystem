package com.infrastructure.callnotificationsystem.repository;

import com.infrastructure.callnotificationsystem.entity.CallHistory;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CallHistoryRepository extends CrudRepository<CallHistory, String> {
    CallHistory findByCalledUserAndCallerUser(String calledUser, String callerUser);
}
