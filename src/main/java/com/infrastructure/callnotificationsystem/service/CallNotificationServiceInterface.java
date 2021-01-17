package com.infrastructure.callnotificationsystem.service;

import java.util.Optional;

public interface CallNotificationServiceInterface {
    Optional<String> getCallHistoryMessage(String calledUser);
}
