package ru.practicum.exception;

public class EventUpdateConflictException extends RuntimeException {

  public EventUpdateConflictException() {

    super("Cannot publish the event because it's not in the right state: PUBLISHED");
  }
}
