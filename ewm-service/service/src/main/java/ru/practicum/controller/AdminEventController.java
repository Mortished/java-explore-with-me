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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.practicum.model.dto.AdminEventRequestDTO;
import ru.practicum.model.dto.EventDTO;
import ru.practicum.service.EventService;

@RestController
@AllArgsConstructor
@RequestMapping("/admin/events")
@Validated
@Slf4j
public class AdminEventController {

  private final EventService eventService;

  @GetMapping
  List<EventDTO> findEvents(
      @RequestParam(required = false) List<Long> users,
      @RequestParam(required = false) List<String> states,
      @RequestParam(required = false) List<Long> categories,
      @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime rangeStart,
      @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime rangeEnd,
      @RequestParam(required = false, defaultValue = "0") @Min(0) Integer from,
      @RequestParam(required = false, defaultValue = "10") @Min(1) Integer size
  ) {
    log.info(
        "GET /admin/events: users={}, states={}, categories={}, rangeStart={}, rangeEnd={}, from={}, size={}",
        users, states, categories, rangeStart, rangeEnd, from, size);
    return eventService.findByParams(users, states, categories, rangeStart, rangeEnd, from, size);
  }

  @PatchMapping("/{eventId}")
  EventDTO updateEvent(@PathVariable Long eventId, @Valid @RequestBody AdminEventRequestDTO body) {
    log.info("PATCH /admin/events/{eventId}: eventId={}, body={}", eventId, body);
    return eventService.update(eventId, body);
  }

}