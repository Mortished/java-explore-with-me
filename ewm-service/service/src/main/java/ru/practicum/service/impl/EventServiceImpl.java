package ru.practicum.service.impl;

import static ru.practicum.utils.Dictionary.EVENT_NAME;

import java.time.LocalDateTime;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.exception.NotFoundException;
import ru.practicum.model.dto.EventDTO;
import ru.practicum.model.dto.EventRequestDTO;
import ru.practicum.repository.EventRepository;
import ru.practicum.service.EventService;

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
    eventRepository.findById(eventId)
        .orElseThrow(() -> new NotFoundException(EVENT_NAME, eventId.toString()));
    return null;
  }

}
