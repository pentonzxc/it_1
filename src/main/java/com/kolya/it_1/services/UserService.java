package com.kolya.it_1.services;


import com.kolya.it_1.domain.User;

import java.util.List;
import java.util.Optional;

public interface UserService {

    List<User> findAllUsers();

    Optional<User> findUserById(Long id);

    void deleteUserById(Long id);

    void addUser(User user);

    void saveUser(User user);

    void blockUserByEmail(String email);

    void unblockUserByEmail(String email);

    void deleteUserByEmail(String email);

    List<User> findAllLoginUsers();

    Optional<User> findUserByEmail(String email);
}
