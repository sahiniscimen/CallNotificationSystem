package com.infrastructure.callnotificationsystem.service;

import com.infrastructure.callnotificationsystem.dto.UserDTO;
import com.infrastructure.callnotificationsystem.entity.User;
import com.infrastructure.callnotificationsystem.exception.UserAlreadyExistsException;
import com.infrastructure.callnotificationsystem.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @Test
    void whenCreateUserCalledAndDoesNotExistThenCreatedSuccessfully(){
        UserDTO userDTO = new UserDTO("05001001010");

        given(userRepository.findByPhoneNumber("05001001010")).willReturn(Optional.empty());
        given(userRepository.save(any())).willAnswer(invocationOnMock -> invocationOnMock.getArguments()[0]);

        User createdUser = userService.createUser(userDTO);

        assertEquals(createdUser.getPhoneNumber(), userDTO.getPhoneNumber());
    }

    @Test
    void whenCreateUserCalledAlreadyExistsThenThrowException(){
        UserDTO userDTO = new UserDTO("05001001010");

        given(userRepository.findByPhoneNumber("05001001010")).willReturn(Optional.of(new User("05001001010")));

        assertThrows(UserAlreadyExistsException.class, () -> userService.createUser(userDTO));
    }
}
