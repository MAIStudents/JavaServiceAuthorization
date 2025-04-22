package ru.mai.lessons.rpks.services;

import ru.mai.lessons.rpks.models.User;

import java.util.Optional;

public interface UserService {

  User createUser(User user);

  User loadUserByUsername(String username);

  Optional<User> findUserByUsername(String username);
}
