package com.infrastructure.callnotificationsystem.entity;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table
public class CallHistory {
    @Id
    @NonNull
    private String calledUser;
    @NonNull
    private String callerUser;
    @NonNull
    private LocalDateTime lastCallDateTime;
    @NonNull
    private int numberOfCalls;
}
