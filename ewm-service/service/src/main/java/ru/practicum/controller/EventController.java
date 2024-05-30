package ru.practicum.controller;

import java.time.LocalDateTime;
import java.util.List;
import javax.validation.Valid;
import javax.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.practicum.model.EventDTO;
import ru.practicum.model.EventRequestDTO;
import ru.practicum.service.EventService;

@RestController
@AllArgsConstructor
@Validated
@Slf4j
public class EventController {

  private final EventService eventService;

  @GetMapping("/admin/events")
  List<EventDTO> findEvents(
      @RequestParam(required = false) List<Long> users,
      @RequestParam(required = false) List<String> states,
      @RequestParam(required = false) List<String> categories,
      @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime rangeStart,
      @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime rangeEnd,
      @RequestParam(required = false) @Min(0) Integer from,
      @RequestParam(required = false) @Min(1) Integer size
  ) {
    log.info(
        "GET /admin/events: users={}, states={}, categories={}, rangeStart={}, rangeEnd={}, from={}, size={}",
        users, states, categories, rangeStart, rangeEnd, from, size);
    return eventService.findByParams(users, states, categories, rangeStart, rangeEnd, from, size);
  }

  @PatchMapping("/admin/events/{eventId}")
  EventDTO updateEvent(@PathVariable Long eventId, @Valid @RequestBody EventRequestDTO body) {
    log.info("PATCH /admin/events/{eventId}: eventId={}, body={}", eventId, body);
    return eventService.update(eventId, body);
  }

}
