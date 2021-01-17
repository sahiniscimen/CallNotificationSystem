package com.infrastructure.callnotificationsystem.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@RequiredArgsConstructor
public class CallHistoryDTO {
    @NonNull
    @JsonProperty("CalledPhoneNumber")
    private String calledUser;
    @JsonProperty("CallerPhoneNumber")
    @NonNull
    private String callerUser;
}
