package com.uhk.moviedb.service;

import com.uhk.moviedb.model.Profile;
import com.uhk.moviedb.model.User;
import com.uhk.moviedb.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    UserRepository userRepository;
    PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void save(User user) {
        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);
        if(user.getProfile() == null) {
            user.setProfile(new Profile());
        }
        userRepository.save(user);
    }

    @Override
    public Optional<User> findUserById(Long id) {
        return userRepository.findById(id);
    }

    @Override
    public User findUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public void delete(User user) {
        userRepository.delete(user);
    }

    @Override
    public boolean checkPassword(String userName, String password) {
        String encodedPassword = passwordEncoder.encode(password);
        return userRepository.checkPassword(userName, encodedPassword);
    }

    @Override
    public boolean checkUsername(String username) {
        return userRepository.checkUsername(username);
    }

    @Override
    public List<User> searchUserByUsername(String value) {
        return userRepository.findByUsernameContaining(value);
    }

}
