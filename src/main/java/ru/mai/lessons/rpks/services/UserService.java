package ru.mai.lessons.rpks.services;

import ru.mai.lessons.rpks.models.User;

public interface UserService {

  User createUser(User user);

  User loadUserByUsername(String username);

  boolean isUserPresent(String username);
}
