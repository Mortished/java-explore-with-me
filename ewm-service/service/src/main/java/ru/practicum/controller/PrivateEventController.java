package ru.practicum.controller;

import java.util.List;
import javax.validation.Valid;
import javax.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import ru.practicum.model.dto.EventDTO;
import ru.practicum.model.dto.EventRequestStatusUpdateRequest;
import ru.practicum.model.dto.EventRequestStatusUpdateResult;
import ru.practicum.model.dto.EventShortInfoDTO;
import ru.practicum.model.dto.NewEventDTO;
import ru.practicum.model.dto.ParticipationRequestDto;
import ru.practicum.model.dto.UpdateEventUserRequestDTO;
import ru.practicum.service.EventService;

@RestController
@AllArgsConstructor
@RequestMapping("/users")
@Validated
@Slf4j
public class PrivateEventController {

  private final EventService eventService;

  @GetMapping("/{userId}/events")
  public List<EventShortInfoDTO> findEventsByUser(@PathVariable Long userId,
      @RequestParam(required = false, defaultValue = "0") @Min(0) Integer from,
      @RequestParam(required = false, defaultValue = "10") @Min(1) Integer size
  ) {
    log.info("GET /users/{userId}/events: userId={}", userId);
    return eventService.findEventsByUser(userId, from, size);
  }

  @PostMapping("/{userId}/events")
  @ResponseStatus(HttpStatus.CREATED)
  public EventDTO create(@PathVariable Long userId, @Valid @RequestBody NewEventDTO eventDTO) {
    log.info("POST /users/{userId}/events: userId={}, body={}", userId, eventDTO);
    return eventService.create(userId, eventDTO);
  }

  @GetMapping("/{userId}/events/{eventId}")
  public EventDTO get(@PathVariable Long userId, @PathVariable Long eventId) {
    log.info("GET /users/{userId}/events/{eventId} : userId={}, eventId={}", userId, eventId);
    return eventService.findUserEventById(userId, eventId);
  }

  @PatchMapping("/{userId}/events/{eventId}")
  public EventDTO updateUserEvent(@PathVariable Long userId, @PathVariable Long eventId,
      @Valid @RequestBody UpdateEventUserRequestDTO body) {
    log.info("PATCH /users/{userId}/events/{eventId} : userId={}, eventId={}, body={}", userId,
        eventId, body);
    return eventService.updateUserEvent(userId, eventId, body);
  }

  @GetMapping("/{userId}/events/{eventId}/requests")
  public List<ParticipationRequestDto> findEventRequests(@PathVariable Long userId, @PathVariable Long eventId) {
    log.info("GET /users/{userId}/events/{eventId}/requests: userId={}, eventId={}", userId,
        eventId);
    //TODO добавить вызов сервиса и логику метода
    return null;
  }

  @PatchMapping("/{userId}/events/{eventId}/requests")
  public EventRequestStatusUpdateResult updateEventRequest(@PathVariable Long userId, @PathVariable Long eventId,
      @Valid @RequestBody EventRequestStatusUpdateRequest body) {
    log.info("PATCH /users/{userId}/events/{eventId}/requests: userId={}, eventId={}, body={}", userId, eventId, body);
    //TODO добавить вызов сервиса и логику метода
    return null;
  }

}
