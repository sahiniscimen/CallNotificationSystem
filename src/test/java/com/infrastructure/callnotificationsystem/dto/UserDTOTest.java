package com.infrastructure.callnotificationsystem.dto;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class UserDTOTest {

    @Test
    void createUserDTO(){
        UserDTO userDTO = new UserDTO("05001001010");

        assertEquals("05001001010", userDTO.getPhoneNumber());
    }
}
