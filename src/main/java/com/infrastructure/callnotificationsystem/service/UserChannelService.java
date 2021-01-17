package com.infrastructure.callnotificationsystem.service;

import com.infrastructure.callnotificationsystem.dto.UserDTO;
import com.infrastructure.callnotificationsystem.entity.User;
import com.infrastructure.callnotificationsystem.repository.UserRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
@Log4j2
public class UserChannelService implements UserChannelServiceInterface {

    @Autowired
    private UserRepository userRepository;

    @Override
    public Optional<UserDTO> getUserByPhoneNumber(String phoneNumber) {
        Optional<User> optionalUser = userRepository.findByPhoneNumber(phoneNumber);
        return optionalUser.isPresent()?
                Optional.of(new UserDTO(optionalUser.get().getPhoneNumber(), optionalUser.get().getPassword())):
                Optional.empty();
    }
}
