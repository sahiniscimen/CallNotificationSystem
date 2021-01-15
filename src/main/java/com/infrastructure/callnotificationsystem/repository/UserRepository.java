package com.infrastructure.callnotificationsystem.repository;

import com.infrastructure.callnotificationsystem.entity.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<User, String> {
}
