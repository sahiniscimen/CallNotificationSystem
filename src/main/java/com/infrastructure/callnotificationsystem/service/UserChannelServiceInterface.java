package com.infrastructure.callnotificationsystem.service;

import com.infrastructure.callnotificationsystem.dto.UserDTO;
import com.infrastructure.callnotificationsystem.entity.User;

import java.util.List;
import java.util.Optional;

public interface UserChannelServiceInterface {
    Optional<UserDTO> getUserByPhoneNumber(String phoneNumber);
}
