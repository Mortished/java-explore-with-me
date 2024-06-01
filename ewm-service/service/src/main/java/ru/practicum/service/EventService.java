package ru.practicum.service;

import java.time.LocalDateTime;
import java.util.List;
import ru.practicum.model.EventSortType;
import ru.practicum.model.dto.EventDTO;
import ru.practicum.model.dto.EventShortInfoDTO;
import ru.practicum.model.dto.NewEventDTO;
import ru.practicum.model.dto.UpdateEventAdminRequestDTO;
import ru.practicum.model.dto.UpdateEventUserRequestDTO;

public interface EventService {

  List<EventDTO> findByParams(List<Long> users, List<String> states, List<Long> categories,
      LocalDateTime rangeStart, LocalDateTime rangeEnd, Integer from, Integer size);

  List<EventDTO> findByParams(String text, List<Long> categories, Boolean paid,
      LocalDateTime rangeStart, LocalDateTime rangeEnd, Boolean onlyAvailable, EventSortType sort,
      Integer from, Integer size);

  EventDTO update(Long eventId, UpdateEventAdminRequestDTO body);

  EventDTO findById(Long eventId);

  List<EventShortInfoDTO> findEventsByUser(Long userId, Integer from, Integer size);

  EventDTO create(Long userId, NewEventDTO body);

  EventDTO findUserEventById(Long userId, Long eventId);

  EventDTO updateUserEvent(Long userId, Long eventId, UpdateEventUserRequestDTO body);

}
