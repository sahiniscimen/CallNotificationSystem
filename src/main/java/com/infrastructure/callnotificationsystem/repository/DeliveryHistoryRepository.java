package com.infrastructure.callnotificationsystem.repository;

import com.infrastructure.callnotificationsystem.entity.DeliveryHistory;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DeliveryHistoryRepository extends CrudRepository<DeliveryHistory, Long> {
    boolean existsByCallerUser(String callerUser);
    List<DeliveryHistory> findByCallerUser(String callerUser);
    void deleteByCallerUser(String callerUser);
}
