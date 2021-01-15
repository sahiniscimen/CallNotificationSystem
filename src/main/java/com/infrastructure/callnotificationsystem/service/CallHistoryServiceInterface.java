package com.infrastructure.callnotificationsystem.service;


import com.infrastructure.callnotificationsystem.dto.CallHistoryDTO;
import com.infrastructure.callnotificationsystem.entity.CallHistory;

public interface CallHistoryServiceInterface {
    CallHistory createCallHistory(CallHistoryDTO callHistoryDTO);
}
