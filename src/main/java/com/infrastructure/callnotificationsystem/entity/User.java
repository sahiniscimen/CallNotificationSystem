package com.infrastructure.callnotificationsystem.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Getter
@Setter
@AllArgsConstructor
@Table
public class User {
    @Id
    @NonNull
    private String phoneNumber;
}
