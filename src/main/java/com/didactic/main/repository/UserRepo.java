package com.devrezaur.main.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.devrezaur.main.model.User;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepo extends JpaRepository<User, Long> {
    User findByName(String name);
}