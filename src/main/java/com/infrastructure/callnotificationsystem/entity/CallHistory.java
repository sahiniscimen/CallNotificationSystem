package com.infrastructure.callnotificationsystem.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@AllArgsConstructor
@Table
public class CallHistory {
    @Id
    @NonNull
    private String calledUser;
    @NonNull
    private String callerUser;
    @NonNull
    private LocalDateTime callDateTime;
}
