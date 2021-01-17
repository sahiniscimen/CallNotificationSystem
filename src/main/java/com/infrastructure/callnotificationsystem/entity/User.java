package com.infrastructure.callnotificationsystem.entity;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Getter
@Setter
@RequiredArgsConstructor
@NoArgsConstructor
@Table
public class User {
    @Id
    @GeneratedValue
    private Long id;
    @NonNull
    private String phoneNumber;
    @NonNull
    private String password;
}
