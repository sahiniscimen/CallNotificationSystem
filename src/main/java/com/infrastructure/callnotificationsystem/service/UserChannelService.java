package com.infrastructure.callnotificationsystem.service;

import com.infrastructure.callnotificationsystem.entity.User;
import com.infrastructure.callnotificationsystem.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class UserChannelService implements UserChannelServiceInterface {

    @Autowired
    private UserRepository userRepository;

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }
}
