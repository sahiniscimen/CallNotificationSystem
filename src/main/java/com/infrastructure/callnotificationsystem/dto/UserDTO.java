package com.infrastructure.callnotificationsystem.dto;

import lombok.Data;
import lombok.NonNull;

@Data
public class UserDTO {
    @NonNull
    private String phoneNumber;
}
