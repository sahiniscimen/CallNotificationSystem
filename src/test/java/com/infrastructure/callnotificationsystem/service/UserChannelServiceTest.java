package com.infrastructure.callnotificationsystem.service;

import com.infrastructure.callnotificationsystem.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class UserChannelServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserChannelService userChannelService;

    @Test
    void whenFindAllCalledThenReturnSuccessfully(){

        userChannelService.getAllUsers();

        verify(userRepository, times(1)).findAll();
    }
}
