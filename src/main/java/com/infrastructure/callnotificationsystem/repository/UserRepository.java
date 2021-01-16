package com.infrastructure.callnotificationsystem.repository;

import com.infrastructure.callnotificationsystem.entity.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<User, String> {
    Optional<User> findByPhoneNumber(String phoneNumber);
    @Override
    List<User> findAll();
}
