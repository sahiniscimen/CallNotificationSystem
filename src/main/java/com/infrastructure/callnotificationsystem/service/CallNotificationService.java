package com.infrastructure.callnotificationsystem.service;

import com.infrastructure.callnotificationsystem.entity.CallHistory;
import com.infrastructure.callnotificationsystem.mapper.CallHistoryMapper;
import com.infrastructure.callnotificationsystem.repository.CallHistoryRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@Log4j2
public class CallNotificationService implements CallNotificationServiceInterface{

    @Autowired
    private CallHistoryMapper callHistoryMapper;

    @Autowired
    private CallHistoryRepository callHistoryRepository;


    @Override
    public Optional<String> getCallHistoryMessage(String calledUser) {
        List<CallHistory> callHistoryList = getCallHistory(calledUser);
        return callHistoryList.isEmpty() ?
                Optional.empty():
                Optional.of(callHistoryMapper.convertEntitiesToMessage(callHistoryList));
    }

    @Override
    public void deleteCallHistory(String calledUser) {
        if (callHistoryRepository.existsByCalledUser(calledUser))
            callHistoryRepository.deleteByCalledUser(calledUser);
    }

    private List<CallHistory> getCallHistory(String calledUser){
        return callHistoryRepository.findByCalledUser(calledUser);
    }
}

