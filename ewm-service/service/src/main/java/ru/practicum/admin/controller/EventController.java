package ru.practicum.admin.controller;

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
import ru.practicum.admin.service.EventService;
import ru.practicum.model.EventDTO;
import ru.practicum.model.EventRequestDTO;

@RestController
@AllArgsConstructor
@RequestMapping("/admin/events")
@Validated
@Slf4j
public class EventController {

  private final EventService eventService;

  @GetMapping()
  List<EventDTO> findEvents(
      @RequestParam(required = false) List<Long> users,
      @RequestParam(required = false) List<String> states,
      @RequestParam(required = false) List<String> categories,
      @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime rangeStart,
      @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime rangeEnd,
      @RequestParam(required = false) @Min(0) Integer from,
      @RequestParam(required = false) @Min(1) Integer size
  ) {
    return eventService.findByParams(users, states, categories, rangeStart, rangeEnd, from, size);
  }

  @PatchMapping("/{eventId}")
  EventDTO updateEvent(@PathVariable Long eventId, @Valid @RequestBody EventRequestDTO body) {
    return eventService.update(eventId, body);
  }

}