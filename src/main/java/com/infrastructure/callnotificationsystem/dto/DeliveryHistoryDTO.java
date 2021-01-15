package com.infrastructure.callnotificationsystem.dto;

import lombok.Data;
import lombok.NonNull;

@Data
public class DeliveryHistoryDTO {
    @NonNull
    private String callerUser;
    @NonNull
    private String deliveredUser;
}
