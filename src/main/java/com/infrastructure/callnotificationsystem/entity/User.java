package com.infrastructure.callnotificationsystem.entity;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table
public class User {
    @Id
    @NonNull
    private String phoneNumber;
}
