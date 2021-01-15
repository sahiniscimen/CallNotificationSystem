package com.infrastructure.callnotificationsystem.mapper;

import com.infrastructure.callnotificationsystem.dto.CallHistoryDTO;
import com.infrastructure.callnotificationsystem.entity.CallHistory;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class CallHistoryMapper {

    public static final String LANGUAGE = "eng";

    public CallHistory createEntityFromCallHistoryDTO(CallHistoryDTO callHistoryDTO, LocalDateTime callDateTime, int numberOfCalls){
        return new CallHistory(callHistoryDTO.getCalledUser(), callHistoryDTO.getCallerUser(), callDateTime, numberOfCalls);
    }

    public String convertEntitiesToMessage(List<CallHistory> callHistoryList){
        return (LANGUAGE.equals("eng") ?
                "Missed calls: " + stringifyCallHistoryList(callHistoryList) :
                "Sizi arayan numaralar: " + stringifyCallHistoryList(callHistoryList));
    }

    private static String stringifyCallHistoryList(List<CallHistory> callHistoryList){
        return callHistoryList.stream()
                .map(callHistory -> callHistory.getCallerUser() + " " + callHistory.getLastCallDateTime().toString()+ " " + callHistory.getNumberOfCalls()+ "/n")
                .collect(Collectors.joining());
    }
}
