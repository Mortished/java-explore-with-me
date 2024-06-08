package ru.practicum.service.impl;

import static ru.practicum.utils.Dictionary.CATEGORY_NAME;
import static ru.practicum.utils.Dictionary.EVENT_NAME;
import static ru.practicum.utils.Dictionary.USER_NAME;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import javax.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import ru.practicum.client.StatsClient;
import ru.practicum.entity.Category;
import ru.practicum.entity.Event;
import ru.practicum.entity.User;
import ru.practicum.exception.ConflictException;
import ru.practicum.exception.NotFoundException;
import ru.practicum.mapper.EventMapper;
import ru.practicum.model.AdminEventRequestStatus;
import ru.practicum.model.EventSortType;
import ru.practicum.model.EventStatus;
import ru.practicum.model.UserEventRequestStatus;
import ru.practicum.model.dto.EventFullDTO;
import ru.practicum.model.dto.EventShortDTO;
import ru.practicum.model.dto.NewEventDTO;
import ru.practicum.model.dto.UpdateEventAdminRequestDTO;
import ru.practicum.model.dto.UpdateEventUserRequestDTO;
import ru.practicum.repository.CategoryRepository;
import ru.practicum.repository.EventRepository;
import ru.practicum.repository.UserRepository;
import ru.practicum.service.EventService;

@Slf4j
@Service
@AllArgsConstructor
public class EventServiceImpl implements EventService {

  private final EventRepository eventRepository;
  private final CategoryRepository categoryRepository;
  private final UserRepository userRepository;
  private final ModelMapper modelMapper;
  private final ObjectMapper objectMapper;
  private final StatsClient statsClient;


  @Override
  @SneakyThrows
  public List<EventFullDTO> findByParams(List<Long> users, List<String> states,
      List<Long> categories, LocalDateTime rangeStart, LocalDateTime rangeEnd, Integer from,
      Integer size) {

    List<Event> events = eventRepository.findByAllParams(users, states, categories, rangeStart,
        rangeEnd, from, size);

    List<EventFullDTO> result = new ArrayList<>();

    for (Event event : events) {
      EventFullDTO eventFullDTO = EventMapper.toEventFullDTO(event);
      result.add(eventFullDTO);
    }

    return result;
  }

  @Override
  public List<EventShortDTO> findByParams(String text, List<Long> categories, Boolean paid,
      LocalDateTime rangeStart, LocalDateTime rangeEnd, Boolean onlyAvailable, EventSortType sort,
      Integer from, Integer size, HttpServletRequest request
  ) {
    String sortBy = sort == null ? null : sort.name();

    List<Event> events = eventRepository.findByAllParams(text, categories, paid, rangeStart,
        rangeEnd, onlyAvailable, sortBy, from, size);

    //statsClient.hit(request.getRequestURI(), request.getRemoteAddr(), LocalDateTime.now());

    return events.stream()
        .map(it -> modelMapper.map(it, EventShortDTO.class))
        .collect(Collectors.toList());
  }

  @Override
  @SneakyThrows
  public EventFullDTO update(Long eventId, UpdateEventAdminRequestDTO body) {
    Event event = eventRepository.findById(eventId)
        .orElseThrow(() -> new NotFoundException(EVENT_NAME, eventId.toString()));

    if (!event.getState().equals(EventStatus.PENDING)) {
      throw new ConflictException();
    }

    Category category = null;
    if (body.getCategory() != null) {
      category = categoryRepository.findById(body.getCategory())
          .orElseThrow(() -> new NotFoundException(CATEGORY_NAME, body.getCategory().toString()));
      event.setCategory(category);
    }

    if (body.getAnnotation() != null) {
      event.setAnnotation(body.getAnnotation());
    }
    if (body.getDescription() != null) {
      event.setDescription(body.getDescription());
    }
    if (body.getEventDate() != null) {
      if (checkEventDate(body.getEventDate())) {
        throw new ConflictException();
      }
      event.setEventDate(body.getEventDate());
    }
    if (body.getLocation() != null) {
      event.setLocation(objectMapper.writeValueAsString(body.getLocation()));
    }
    if (body.getPaid() != null) {
      event.setPaid(body.getPaid());
    }
    if (body.getParticipantLimit() != null) {
      event.setParticipantLimit(body.getParticipantLimit());
    }
    if (body.getRequestModeration() != null) {
      event.setRequestModeration(body.getRequestModeration());
    }
    if (body.getStateAction() != null) {
      EventStatus status = body.getStateAction().equals(AdminEventRequestStatus.PUBLISH_EVENT)
          ? EventStatus.PUBLISHED : EventStatus.CANCELED;
      event.setState(status);
    }
    if (body.getTitle() != null) {
      event.setTitle(body.getTitle());
    }

    Event result = eventRepository.saveAndFlush(event);

    return EventMapper.toEventFullDTO(result);
  }

  @Override
  @SneakyThrows
  public EventFullDTO findById(Long eventId, HttpServletRequest request) {
    Event event = eventRepository.findById(eventId)
        .orElseThrow(() -> new NotFoundException(EVENT_NAME, eventId.toString()));

    if (!event.getState().equals(EventStatus.PUBLISHED)) {
      throw new NotFoundException(EVENT_NAME, eventId.toString());
    }
    event.setViews(event.getViews() + 1);
    Event result = eventRepository.saveAndFlush(event);

    statsClient.hit(request.getRequestURI(), request.getRemoteAddr(), LocalDateTime.now());
    return EventMapper.toEventFullDTO(result);
  }

  @Override
  public List<EventShortDTO> findEventsByUser(Long userId, Integer from, Integer size) {
    userRepository.findById(userId)
        .orElseThrow(() -> new NotFoundException(USER_NAME, userId.toString()));

    return eventRepository.findAllByUserId(userId, from, size).stream()
        .map(it -> modelMapper.map(it, EventShortDTO.class))
        .collect(Collectors.toList());
  }

  @Override
  @SneakyThrows
  public EventFullDTO create(Long userId, NewEventDTO body) {
    User user = userRepository.findById(userId)
        .orElseThrow(() -> new NotFoundException(USER_NAME, userId.toString()));

    if (checkEventDate(body.getEventDate())) {
      throw new ConflictException();
    }
    Category category = categoryRepository.findById(body.getCategory())
        .orElseThrow(() -> new NotFoundException(CATEGORY_NAME, body.getCategory().toString()));

    Event event = EventMapper.toEvent(body, category);

    event.setInitiator(user);
    event.setCreatedOn(LocalDateTime.now());
    if (event.isRequestModeration()) {
      event.setState(EventStatus.PENDING);
    } else {
      event.setState(EventStatus.PUBLISHED);
      event.setPublishedOn(LocalDateTime.now());
    }

    Event result = eventRepository.saveAndFlush(event);

    return EventMapper.toEventFullDTO(result);
  }

  @Override
  @SneakyThrows
  public EventFullDTO findUserEventById(Long userId, Long eventId) {
    User user = userRepository.findById(userId)
        .orElseThrow(() -> new NotFoundException(USER_NAME, userId.toString()));

    Event event = eventRepository.findById(eventId)
        .orElseThrow(() -> new NotFoundException(EVENT_NAME, eventId.toString()));

    if (!event.getInitiator().equals(user)) {
      log.warn("Юзер из запроса != юзеру события");
      throw new NotFoundException(EVENT_NAME, eventId.toString());
    }

    return EventMapper.toEventFullDTO(event);
  }

  @Override
  @SneakyThrows
  public EventFullDTO updateUserEvent(Long userId, Long eventId, UpdateEventUserRequestDTO body) {
    User user = userRepository.findById(userId)
        .orElseThrow(() -> new NotFoundException(USER_NAME, userId.toString()));

    Event event = eventRepository.findById(eventId)
        .orElseThrow(() -> new NotFoundException(EVENT_NAME, eventId.toString()));

    if (event.getState().equals(EventStatus.PUBLISHED)) {
      throw new ConflictException();
    }

    if (body.getCategory() != null) {
      Category category = categoryRepository.findById(body.getCategory())
          .orElseThrow(() -> new NotFoundException(CATEGORY_NAME, body.getCategory().toString()));
      event.setCategory(category);
    }

    if (body.getAnnotation() != null) {
      event.setAnnotation(body.getAnnotation());
    }
    if (body.getDescription() != null) {
      event.setDescription(body.getDescription());
    }
    if (body.getEventDate() != null) {
      if (checkEventDate(body.getEventDate())) {
        throw new ConflictException();
      }
      event.setEventDate(body.getEventDate());
    }
    if (body.getPaid() != null) {
      event.setPaid(body.getPaid());
    }
    if (body.getParticipantLimit() != null) {
      event.setParticipantLimit(body.getParticipantLimit());
    }
    if (body.getTitle() != null) {
      event.setTitle(body.getTitle());
    }
    if (body.getLocation() != null) {
      event.setLocation(objectMapper.writeValueAsString(body.getLocation()));
    }
    if (body.getStateAction() != null) {
      EventStatus status = body.getStateAction().equals(UserEventRequestStatus.CANCEL_REVIEW)
          ? EventStatus.CANCELED : EventStatus.PENDING;
      event.setState(status);
    }

    Event result = eventRepository.saveAndFlush(event);

    return EventMapper.toEventFullDTO(result);
  }

  private boolean checkEventDate(LocalDateTime eventDate) {
    return Duration.between(LocalDateTime.now(), eventDate).toHours() < 2L;
  }

}
