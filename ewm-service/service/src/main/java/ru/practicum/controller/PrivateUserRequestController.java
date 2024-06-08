package ru.practicum.controller;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import ru.practicum.model.dto.ParticipationRequestDto;
import ru.practicum.service.RequestService;

@RestController
@AllArgsConstructor
@RequestMapping("/users")
@Validated
@Slf4j
public class PrivateUserRequestController {

  private final RequestService requestService;

  @GetMapping("/{userId}/requests")
  public List<ParticipationRequestDto> getRequests(@PathVariable("userId") Long userId) {
    log.info("GET /users/{userId}/requests: userId={}", userId);
    return requestService.findAllByUserId(userId);
  }

  @PostMapping("/{userId}/requests")
  @ResponseStatus(HttpStatus.CREATED)
  public ParticipationRequestDto createRequest(@PathVariable("userId") Long userId, @RequestParam Long eventId) {
    log.info("POST /users/{userId}/requests: userId={}, eventId={}", userId, eventId);
    return requestService.createRequest(userId, eventId);
  }

  @PatchMapping("/{userId}/requests/{requestId}/cancel")
  public ParticipationRequestDto cancelRequest(@PathVariable("userId") Long userId, @PathVariable("requestId") Long requestId) {
    log.info("PATCH /{userId}/requests/{requestId}/cancel: userId={}, requestId={}", userId, requestId);
    return requestService.cancelRequest(userId, requestId);
  }

}
