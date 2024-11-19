package com.uhk.moviedb.service;

import com.uhk.moviedb.model.User;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public interface UserService {
    void createUser(User user);
    Optional<User> findUserById(Long id);
}
