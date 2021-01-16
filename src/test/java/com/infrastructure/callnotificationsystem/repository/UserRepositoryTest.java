package com.infrastructure.callnotificationsystem.repository;

import com.infrastructure.callnotificationsystem.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.TestPropertySource;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@TestPropertySource(properties = {
        "spring.jpa.hibernate.ddl-auto=update"
})
class UserRepositoryTest {

    @Autowired UserRepository userRepository;

    @Test
    void injectedComponentsShouldNotBeNull(){ assertNotNull(userRepository);}

    @Test
    void whenCreatedFindByPhoneNumberCalledThenReturnSuccessfully(){
        User user1 = userRepository.save(new User("05001001010"));

        Optional<User> optionalUser = userRepository.findByPhoneNumber("05001001010");

        assertTrue(optionalUser.isPresent());
        assertEquals(user1.getPhoneNumber(), optionalUser.get().getPhoneNumber());
    }

    @Test
    void whenCreatedSeveralAndFindAllCalledThenReturnListOfAllSuccessfully(){
        User user1 = userRepository.save(new User("05001001010"));
        User user2 = userRepository.save(new User("05001001011"));

        List<User> userList = userRepository.findAll();

        assertTrue(userList.contains(user1));
        assertTrue(userList.contains(user2));
    }
}
