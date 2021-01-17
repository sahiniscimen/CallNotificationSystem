package com.infrastructure.callnotificationsystem.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.validation.constraints.Pattern;

@Getter
@Setter
@NoArgsConstructor
@RequiredArgsConstructor
public class CallHistoryDTO {
    @NonNull
    @JsonProperty("CalledPhoneNumber")
    @Pattern(regexp="[\\d]{10}")
    private String calledUser;
    @NonNull
    @JsonProperty("CallerPhoneNumber")
    @Pattern(regexp="[\\d]{10}")
    private String callerUser;
}
