package com.devrezaur.main.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.devrezaur.main.model.User;
import com.devrezaur.main.repository.UserRepo;

import java.util.Optional;

@Service
public class UserService {
	@Autowired
	private UserRepo userRepository;
	
	@Transactional
	public void addNewUser(User user) {
        userRepository.save(user);
    }

    public User findUserByName(String name) {
	    return userRepository.findByName(name);
    }

    public Optional<User> findUserById(Long id) {
	    return userRepository.findById(id);
    }
}