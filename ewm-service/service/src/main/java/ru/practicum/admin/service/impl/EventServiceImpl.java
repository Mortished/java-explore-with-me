package ru.practicum.admin.service.impl;

import java.time.LocalDateTime;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.admin.repository.EventRepository;
import ru.practicum.admin.service.EventService;
import ru.practicum.model.EventDTO;
import ru.practicum.model.EventRequestDTO;

@Service
@AllArgsConstructor
public class EventServiceImpl implements EventService {

  private final EventRepository eventRepository;

  @Override
  public List<EventDTO> findByParams(List<Long> users, List<String> states, List<String> categories,
      LocalDateTime rangeStart, LocalDateTime rangeEnd, Integer from, Integer size) {
    return List.of();
  }

  @Override
  public EventDTO update(Long eventId, EventRequestDTO body) {
    return null;
  }

}
