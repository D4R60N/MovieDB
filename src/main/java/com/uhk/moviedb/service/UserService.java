package com.uhk.moviedb.service;

import com.uhk.moviedb.model.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public interface UserService {
    void save(User user);
    Optional<User> findUserById(Long id);
    User findUserByUsername(String username);
    void delete(User user);
}
