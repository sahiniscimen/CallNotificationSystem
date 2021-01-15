package com.infrastructure.callnotificationsystem.dto;

import lombok.Data;
import lombok.NonNull;

@Data
public class CallHistoryDTO {
    @NonNull
    private String calledUser;
    @NonNull
    private String callerUser;
}
