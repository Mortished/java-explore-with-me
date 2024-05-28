package ru.practicum.exception;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class CategoryNotFoundException extends RuntimeException {

  public CategoryNotFoundException(String catId) {
    super("Category with id=" + catId + " was not found");
  }
}
