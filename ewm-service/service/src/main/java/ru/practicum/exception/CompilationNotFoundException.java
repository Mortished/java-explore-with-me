package ru.practicum.exception;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class CompilationNotFoundException extends RuntimeException {

  public CompilationNotFoundException(String compId) {
    super("Compilation with id=" + compId + " was not found");
  }
}
