package com.infrastructure.callnotificationsystem.entity;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class UserTest {

    @Test
    void createUser(){
        User user = new User("05001001010");

        assertEquals("05001001010", user.getPhoneNumber());
    }
}
