package com.infrastructure.callnotificationsystem.repository;

import com.infrastructure.callnotificationsystem.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.TestPropertySource;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@DataJpaTest
@TestPropertySource(properties = {
        "spring.jpa.hibernate.ddl-auto=update"
})
class UserRepositoryTest {

    @Autowired UserRepository userRepository;

    @Test
    void injectedComponentsShouldNotBeNull(){ assertNotNull(userRepository);}

    @Test
    void whenCreatedThenFindByPhoneNumber(){
        userRepository.save(new User("05001001010"));

        User user = userRepository.findById("05001001010").get();

        assertEquals("05001001010", user.getPhoneNumber());
    }
}
