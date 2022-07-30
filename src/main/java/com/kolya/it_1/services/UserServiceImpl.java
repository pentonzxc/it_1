package com.kolya.it_1.services;

import com.kolya.it_1.domain.User;
import com.kolya.it_1.exceptions.UserNotFoundException;
import com.kolya.it_1.repository.UserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    public final UserRepository userRepository;

    public final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public List<User> findAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public Optional<User> findUserById(Long id) {
        return userRepository.findUserById(id);
    }

    @Override
    public void deleteUserById(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    public void addUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        saveUser(user);
    }

    @Override
    public Optional<User> findUserByEmail(String email) {
        return userRepository.findUserByEmail(email);
    }

    @Transactional
    public void saveUser(User user) {
        userRepository.save(user);
    }

    @Override
    public void blockUserByEmail(String email) {
        User user = findUserByEmail(email).
                orElseThrow(() -> new UserNotFoundException(
                        String.format("User with email %s doesn't exist" , email)));
        user.setStatus("BLOCKED");
        saveUser(user);
    }

    @Override
    public void unblockUserByEmail(String email) {
        User user = findUserByEmail(email).
                orElseThrow(() -> new UserNotFoundException(
                        String.format("User with email %s doesn't exist" , email)));
        user.setStatus("ACTIVE");
        saveUser(user);
    }

    @Override
    public void deleteUserByEmail(String email) {
        User user = findUserByEmail(email).
                orElseThrow(() -> new UserNotFoundException(
                        String.format("User with email %s doesn't exist" , email)));
        deleteUserById(user.getId());
    }

    @Override
    public List<User> findAllLoginUsers() {
        return userRepository.findAllByLoginDateNotNull();
    }

}
