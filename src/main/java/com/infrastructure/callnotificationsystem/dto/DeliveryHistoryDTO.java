package com.infrastructure.callnotificationsystem.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@RequiredArgsConstructor
public class DeliveryHistoryDTO {
    @NonNull
    @JsonProperty("CallerPhoneNumber")
    private String callerUser;
    @JsonProperty("DeliveredPhoneNumber")
    @NonNull
    private String deliveredUser;
}
