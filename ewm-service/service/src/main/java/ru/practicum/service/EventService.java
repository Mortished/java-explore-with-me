package ru.practicum.service;

import java.time.LocalDateTime;
import java.util.List;
import ru.practicum.model.EventSortType;
import ru.practicum.model.dto.AdminEventRequestDTO;
import ru.practicum.model.dto.EventDTO;

public interface EventService {

  List<EventDTO> findByParams(List<Long> users, List<String> states, List<Long> categories,
      LocalDateTime rangeStart, LocalDateTime rangeEnd, Integer from, Integer size);

  List<EventDTO> findByParams(String text, List<Long> categories, Boolean paid,
      LocalDateTime rangeStart, LocalDateTime rangeEnd, Boolean onlyAvailable, EventSortType sort,
      Integer from, Integer size);

  EventDTO update(Long eventId, AdminEventRequestDTO body);

  EventDTO findById(Long eventId);

}
