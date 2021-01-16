package com.infrastructure.callnotificationsystem.service;

import com.infrastructure.callnotificationsystem.dto.CallHistoryDTO;
import com.infrastructure.callnotificationsystem.entity.CallHistory;
import com.infrastructure.callnotificationsystem.mapper.CallHistoryMapper;
import com.infrastructure.callnotificationsystem.repository.CallHistoryRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@Log4j2
public class CallHistoryService implements CallHistoryServiceInterface{

    @Autowired
    private CallHistoryMapper callHistoryMapper;

    @Autowired
    private CallHistoryRepository callHistoryRepository;

    @Override
    public CallHistory createOrUpdateCallHistory(CallHistoryDTO callHistoryDTO) {
        Optional<CallHistory> optionalCallHistory = getCallHistory(callHistoryDTO);
        return optionalCallHistory.isPresent() ?
                updateCallHistory(optionalCallHistory.get()):
                callHistoryRepository.save(convertToCallHistory(callHistoryDTO));
    }

    private Optional<CallHistory> getCallHistory(CallHistoryDTO callHistoryDTO){
        return callHistoryRepository
                .findByCalledUserAndCallerUser(callHistoryDTO.getCalledUser(), callHistoryDTO.getCallerUser());
    }

    private CallHistory convertToCallHistory(CallHistoryDTO callHistoryDTO){
        return callHistoryMapper.createEntityFromCallHistoryDTO(callHistoryDTO, LocalDateTime.now(),1);
    }

    private CallHistory updateCallHistory(CallHistory callHistory){
        callHistory.setLastCallDateTime(LocalDateTime.now());
        callHistory.setNumberOfCalls(callHistory.getNumberOfCalls() + 1);
        return callHistoryRepository.save(callHistory);
    }
}
