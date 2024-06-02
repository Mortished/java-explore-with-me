package ru.practicum.controller;

import java.time.LocalDateTime;
import java.util.List;
import javax.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.practicum.model.EventSortType;
import ru.practicum.model.dto.EventFullDTO;
import ru.practicum.service.EventService;

@RestController
@AllArgsConstructor
@RequestMapping("/events")
@Validated
@Slf4j
public class PublicEventController {

  private final EventService eventService;

  @GetMapping
  public List<EventFullDTO> findAllWithParams(
      @RequestParam(required = false) String text,
      @RequestParam(required = false) List<Long> categories,
      @RequestParam(required = false) Boolean paid,
      @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime rangeStart,
      @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime rangeEnd,
      @RequestParam(required = false, defaultValue = "false") Boolean onlyAvailable,
      @RequestParam(required = false) EventSortType sort,
      @RequestParam(required = false, defaultValue = "0") @Min(0) Integer from,
      @RequestParam(required = false, defaultValue = "10") @Min(1) Integer size
  ) {
    log.info(
        "GET /events : text={}, categories={}, paid={}, rangeStart={}, rangeEnd={}, onlyAvailable={}, sort={}, from={}, size={}",
        text, categories, paid, rangeStart, rangeEnd, onlyAvailable, sort, from, size);
    return eventService.findByParams(text, categories, paid, rangeStart, rangeEnd, onlyAvailable,
        sort, from, size);
  }

  @GetMapping("/{id}")
  public EventFullDTO findById(@PathVariable("id") Long id) {
    log.info("GET /events/{}", id);
    return eventService.findById(id);
  }

}
