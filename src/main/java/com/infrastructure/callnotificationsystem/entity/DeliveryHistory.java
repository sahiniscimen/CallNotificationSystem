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
public class DeliveryHistory {
    @Id
    @NonNull
    private String callerUser;
    @NonNull
    private String deliveredUser;
    @NonNull
    private LocalDateTime deliveryDateTime;
    @NonNull
    private LocalDateTime lastCallDateTime;
}
