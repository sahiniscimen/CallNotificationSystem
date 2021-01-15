package com.infrastructure.callnotificationsystem.repository;

import com.infrastructure.callnotificationsystem.entity.DeliveryHistory;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DeliveryHistoryRepository extends CrudRepository<DeliveryHistory, String> {
}
