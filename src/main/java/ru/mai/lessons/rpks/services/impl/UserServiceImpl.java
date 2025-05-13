package ru.mai.lessons.rpks.services.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import ru.mai.lessons.rpks.models.User;
import ru.mai.lessons.rpks.repositories.UserRepository;
import ru.mai.lessons.rpks.services.UserService;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

  private final UserRepository userRepository;

  @Override
  public User createUser(User user) {
    userRepository.save(user);
    return user;
  }

  @Cacheable(value = "UserCache")
  @Override
  public User loadUserByUsername(String username) {
    return userRepository.findByUsername(username).orElse(null);
  }
}
