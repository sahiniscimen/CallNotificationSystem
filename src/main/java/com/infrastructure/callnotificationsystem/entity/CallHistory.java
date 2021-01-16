package com.infrastructure.callnotificationsystem.entity;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@RequiredArgsConstructor
@NoArgsConstructor
@Table
public class CallHistory {
    @Id
    @GeneratedValue
    private Long callHistoryId;
    @NonNull
    private String calledUser;
    @NonNull
    private String callerUser;
    @NonNull
    private LocalDateTime lastCallDateTime;
    @NonNull
    private Integer numberOfCalls;
}
