package ru.practicum.service;

import java.time.LocalDateTime;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import ru.practicum.model.EventSortType;
import ru.practicum.model.dto.EventFullDTO;
import ru.practicum.model.dto.EventShortDTO;
import ru.practicum.model.dto.NewEventDTO;
import ru.practicum.model.dto.UpdateEventAdminRequestDTO;
import ru.practicum.model.dto.UpdateEventUserRequestDTO;

public interface EventService {

  List<EventFullDTO> findByParams(List<Long> users, List<String> states, List<Long> categories,
      LocalDateTime rangeStart, LocalDateTime rangeEnd, Integer from, Integer size);

  List<EventFullDTO> findByParams(String text, List<Long> categories, Boolean paid,
      LocalDateTime rangeStart, LocalDateTime rangeEnd, Boolean onlyAvailable, EventSortType sort,
      Integer from, Integer size, HttpServletRequest request);

  EventFullDTO update(Long eventId, UpdateEventAdminRequestDTO body);

  EventFullDTO findById(Long eventId, HttpServletRequest request);

  List<EventShortDTO> findEventsByUser(Long userId, Integer from, Integer size);

  EventFullDTO create(Long userId, NewEventDTO body);

  EventFullDTO findUserEventById(Long userId, Long eventId);

  EventFullDTO updateUserEvent(Long userId, Long eventId, UpdateEventUserRequestDTO body);

}
