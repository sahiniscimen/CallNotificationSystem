package com.infrastructure.callnotificationsystem.service;

import com.infrastructure.callnotificationsystem.dto.UserDTO;
import com.infrastructure.callnotificationsystem.entity.User;

public interface UserServiceInterface {
    User createUser(UserDTO userDTO);
}
