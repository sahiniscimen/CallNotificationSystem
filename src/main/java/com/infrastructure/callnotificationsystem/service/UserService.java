package com.infrastructure.callnotificationsystem.service;

import com.infrastructure.callnotificationsystem.dto.UserDTO;
import com.infrastructure.callnotificationsystem.entity.User;
import com.infrastructure.callnotificationsystem.exception.UserAlreadyExistsException;
import com.infrastructure.callnotificationsystem.repository.UserRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@Log4j2
public class UserService implements UserServiceInterface {

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private UserRepository userRepository;

    @Override
    public User createUser(UserDTO userDTO) {
        if (userRepository.findByPhoneNumber(userDTO.getPhoneNumber()).isPresent())
            throw new UserAlreadyExistsException("Phone number is already registered.");

        return userRepository.save(new User(userDTO.getPhoneNumber(), bCryptPasswordEncoder.encode(userDTO.getPassword())));
    }
}
