package ru.practicum.exception;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionHandlingControllerAdvice {

  private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
  private static final String OBJECT_NOT_FOUND_REASON = "The required object was not found.";

  @ResponseStatus(HttpStatus.NOT_FOUND)
  @ExceptionHandler(NotFoundException.class)
  public ErrorResponse handleValidationExceptions(NotFoundException ex) {
    return ErrorResponse.builder()
        .status(HttpStatus.NOT_FOUND.name())
        .reason(OBJECT_NOT_FOUND_REASON)
        .message(ex.getMessage())
        .timestamp(LocalDateTime.now().format(formatter))
        .build();
  }

  @ResponseStatus(HttpStatus.CONFLICT)
  @ExceptionHandler(ConstraintViolationException.class)
  public ErrorResponse handleValidationExceptions(ConstraintViolationException ex) {
    return ErrorResponse.builder()
        .status(HttpStatus.CONFLICT.name())
        .reason("Integrity constraint has been violated.")
        .message(ex.getMessage())
        .timestamp(LocalDateTime.now().format(formatter))
        .build();
  }

  @ResponseStatus(HttpStatus.BAD_REQUEST)
  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ErrorResponse handleValidationExceptions(MethodArgumentNotValidException ex) {
    return ErrorResponse.builder()
        .status(HttpStatus.BAD_REQUEST.name())
        .reason("Incorrectly made request.")
        .message("sd")
        .timestamp(LocalDateTime.now().format(formatter))
        .build();
  }

}
