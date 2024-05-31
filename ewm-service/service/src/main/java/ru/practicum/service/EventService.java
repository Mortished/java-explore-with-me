package ru.practicum.service;

import java.time.LocalDateTime;
import java.util.List;
import ru.practicum.model.dto.EventDTO;
import ru.practicum.model.dto.EventRequestDTO;

public interface EventService {

  List<EventDTO> findByParams(List<Long> users, List<String> states, List<String> categories,
      LocalDateTime rangeStart, LocalDateTime rangeEnd, Integer from, Integer size);

  EventDTO update(Long eventId, EventRequestDTO body);

}
