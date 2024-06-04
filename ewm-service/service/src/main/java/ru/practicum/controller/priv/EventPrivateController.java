package ru.practicum.controller.priv;

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
import ru.practicum.model.dto.EventFullDTO;
import ru.practicum.model.dto.EventRequestStatusUpdateRequest;
import ru.practicum.model.dto.EventRequestStatusUpdateResult;
import ru.practicum.model.dto.EventShortDTO;
import ru.practicum.model.dto.NewEventDTO;
import ru.practicum.model.dto.ParticipationRequestDto;
import ru.practicum.model.dto.UpdateEventUserRequestDTO;
import ru.practicum.service.EventService;
import ru.practicum.service.RequestService;

@RestController
@AllArgsConstructor
@RequestMapping("/users")
@Validated
@Slf4j
public class EventPrivateController {

  private final EventService eventService;
  private final RequestService requestService;

  @GetMapping("/{userId}/events")
  public List<EventShortDTO> findEventsByUser(@PathVariable Long userId,
      @RequestParam(required = false, defaultValue = "0") @Min(0) Integer from,
      @RequestParam(required = false, defaultValue = "10") @Min(1) Integer size
  ) {
    log.info("GET /users/{userId}/events: userId={}", userId);
    return eventService.findEventsByUser(userId, from, size);
  }

  @PostMapping("/{userId}/events")
  @ResponseStatus(HttpStatus.CREATED)
  public EventFullDTO create(@PathVariable Long userId, @Valid @RequestBody NewEventDTO eventDTO) {
    log.info("POST /users/{userId}/events: userId={}, body={}", userId, eventDTO);
    return eventService.create(userId, eventDTO);
  }

  @GetMapping("/{userId}/events/{eventId}")
  public EventFullDTO get(@PathVariable Long userId, @PathVariable Long eventId) {
    log.info("GET /users/{userId}/events/{eventId} : userId={}, eventId={}", userId, eventId);
    return eventService.findUserEventById(userId, eventId);
  }

  @PatchMapping("/{userId}/events/{eventId}")
  public EventFullDTO updateUserEvent(@PathVariable Long userId, @PathVariable Long eventId,
      @Valid @RequestBody UpdateEventUserRequestDTO body) {
    log.info("PATCH /users/{userId}/events/{eventId} : userId={}, eventId={}, body={}", userId,
        eventId, body);
    return eventService.updateUserEvent(userId, eventId, body);
  }

  @GetMapping("/{userId}/events/{eventId}/requests")
  public List<ParticipationRequestDto> findEventRequests(@PathVariable Long userId,
      @PathVariable Long eventId) {
    log.info("GET /users/{userId}/events/{eventId}/requests: userId={}, eventId={}", userId,
        eventId);
    return requestService.findRequestsByUserEvent(userId, eventId);
  }

  @PatchMapping("/{userId}/events/{eventId}/requests")
  public EventRequestStatusUpdateResult updateEventRequest(@PathVariable Long userId,
      @PathVariable Long eventId,
      @Valid @RequestBody EventRequestStatusUpdateRequest body) {
    log.info("PATCH /users/{userId}/events/{eventId}/requests: userId={}, eventId={}, body={}",
        userId, eventId, body);
    return requestService.updateRequestsByUserEvent(userId, eventId, body);
  }

}