package ru.practicum.exception;

public class ConflictException extends RuntimeException {

  public ConflictException() {

    super("Modify Conflict");
  }
}
