package ru.practicum.exception;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class UserNotFoundException extends RuntimeException {

  public UserNotFoundException(String userId) {
    super("User with id=" + userId + " was not found");
  }
}
