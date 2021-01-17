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
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {
    @Mock
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @Test
    void whenCreateUserCalledAndDoesNotExistThenCreatedSuccessfully(){
        UserDTO userDTO = new UserDTO("05001001010","12345678");

        given(userRepository.findByPhoneNumber("05001001010")).willReturn(Optional.empty());
        given(userRepository.save(any())).willAnswer(invocationOnMock -> invocationOnMock.getArguments()[0]);
        given(bCryptPasswordEncoder.encode("12345678")).willReturn("321");

        User createdUser = userService.createUser(userDTO);

        assertEquals(userDTO.getPhoneNumber(), createdUser.getPhoneNumber());
        assertEquals("321", createdUser.getPassword());
    }

    @Test
    void whenCreateUserCalledAlreadyExistsThenThrowException(){
        UserDTO userDTO = new UserDTO("05001001010","12345678");

        given(userRepository.findByPhoneNumber("05001001010")).willReturn(Optional.of(new User("05001001010","12345678")));

        assertThrows(UserAlreadyExistsException.class, () -> userService.createUser(userDTO));
    }
}
