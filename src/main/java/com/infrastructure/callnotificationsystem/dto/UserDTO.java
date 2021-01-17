package com.infrastructure.callnotificationsystem.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.validation.constraints.Pattern;

@Getter
@Setter
@NoArgsConstructor
@RequiredArgsConstructor
public class UserDTO {
    @NonNull
    @JsonProperty("PhoneNumber")
    @Pattern(regexp="[\\d]{10}")
    private String phoneNumber;
    @NonNull
    @JsonProperty("Password")
    private String password;
}
