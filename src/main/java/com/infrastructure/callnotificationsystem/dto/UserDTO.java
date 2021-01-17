package com.infrastructure.callnotificationsystem.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@RequiredArgsConstructor
public class UserDTO {
    @NonNull
    @JsonProperty("PhoneNumber")
    private String phoneNumber;
}
