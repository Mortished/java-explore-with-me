package ru.practicum.service.impl;

import static ru.practicum.utils.CustomPageRequest.pageRequestOf;
import static ru.practicum.utils.Dictionary.CATEGORY_NAME;
import static ru.practicum.utils.Dictionary.EVENT_NAME;
import static ru.practicum.utils.Dictionary.USER_NAME;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import javax.servlet.http.HttpServletRequest;
import javax.validation.ValidationException;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;
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
import ru.practicum.model.pojo.EventViewStats;
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
  public List<EventFullDTO> findAdminEventsByParams(List<Long> users, List<String> states,
      List<Long> categories, LocalDateTime rangeStart, LocalDateTime rangeEnd, Integer from,
      Integer size) {

    Specification<Event> specification = Specification.where(null);
    if (users != null) {
      specification = specification.and(
          (root, query, criteriaBuilder) -> root.get("initiator").get("id").in(users));
    }
    if (states != null) {
      specification = specification.and(
          (root, query, criteriaBuilder) -> root.get("state").as(String.class).in(states));
    }
    if (categories != null) {
      specification = specification.and(
          (root, query, criteriaBuilder) -> root.get("category").get("id").in(categories));
    }
    if (rangeStart != null) {
      specification = specification.and(
          (root, query, criteriaBuilder) -> criteriaBuilder.greaterThanOrEqualTo(
              root.get("eventDate"), rangeStart));
    }
    if (rangeEnd != null) {
      specification = specification.and(
          (root, query, criteriaBuilder) -> criteriaBuilder.lessThanOrEqualTo(root.get("eventDate"),
              rangeEnd));
    }

    List<Event> events = eventRepository.findAll(specification, PageRequest.of(from / size, size))
        .getContent();

    List<EventFullDTO> result = new ArrayList<>();

    for (Event event : events) {
      EventFullDTO eventFullDTO = EventMapper.toEventFullDTO(event);
      result.add(eventFullDTO);
    }

    return result;
  }

  @Override
  @SneakyThrows
  public List<EventShortDTO> findPublicEventsByParams(String text, List<Long> categories,
      Boolean paid,
      LocalDateTime rangeStart, LocalDateTime rangeEnd, Boolean onlyAvailable, EventSortType sort,
      Integer from, Integer size, HttpServletRequest request
  ) {

    Specification<Event> specification = Specification.where(null);

    specification = specification.and(
        (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("state"),
            EventStatus.PUBLISHED));

    if (text != null) {
      specification = specification.and((root, query, criteriaBuilder) -> criteriaBuilder.or(
          criteriaBuilder.like(criteriaBuilder.lower(root.get("annotation")),
              "%" + text.toLowerCase() + "%"),
          criteriaBuilder.like(criteriaBuilder.lower(root.get("description")),
              "%" + text.toLowerCase() + "%")));
    }

    if (categories != null) {
      specification = specification.and(
          (root, query, criteriaBuilder) -> root.get("category").get("id").in(categories));
    }

    if (paid != null) {
      specification = specification.and(
          (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("paid"), paid));
    }

    LocalDateTime startDateTime = Objects.requireNonNullElse(rangeStart, LocalDateTime.now());
    specification = specification.and(
        (root, query, criteriaBuilder) -> criteriaBuilder.greaterThan(root.get("eventDate"),
            startDateTime));

    if (rangeEnd != null) {
      specification = specification.and(
          (root, query, criteriaBuilder) -> criteriaBuilder.lessThan(root.get("eventDate"),
              rangeEnd));

      if (rangeStart.isAfter(rangeEnd)) {
        throw new ValidationException();
      }
    }

    if (onlyAvailable != null && onlyAvailable) {
      specification = specification.and(
          (root, query, criteriaBuilder) -> criteriaBuilder.greaterThanOrEqualTo(
              root.get("participantLimit"), 0));
    }
    PageRequest pageRequest = pageRequestOf(from, size);
    if (sort != null) {
      if (sort.equals(EventSortType.EVENT_DATE)) {
        pageRequest = pageRequestOf(from, size, Sort.by("eventDate"));
      } else {
        pageRequest = pageRequestOf(from, size, Sort.by("views").descending());
      }
    }

    List<Event> events = eventRepository.findAll(specification, pageRequest).getContent();

    statsClient.hit(request.getRequestURI(), request.getRemoteAddr(), LocalDateTime.now());

    ResponseEntity<Object> response = getEventsViewStats(events);

    List<EventViewStats> list = objectMapper.readValue(
        objectMapper.writeValueAsString(response.getBody()),
        new TypeReference<>() {
        });

    Map<Long, Long> eventsHits = getEventsHits(list);

    for (Event event : events) {
      event.setViews(eventsHits.get(event.getId()));
    }

    return events.stream().map(it -> modelMapper.map(it, EventShortDTO.class))
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
      if (LocalDateTime.now().isAfter(body.getEventDate())) {
        throw new ValidationException();
      }
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

    String uri = request.getRequestURI();
    statsClient.hit(uri, request.getRemoteAddr(), LocalDateTime.now());

    ResponseEntity<Object> response = statsClient.getStats(event.getCreatedOn(),
        event.getEventDate(), new String[]{uri}, true);

    List<EventViewStats> list = objectMapper.readValue(
        objectMapper.writeValueAsString(response.getBody()), new TypeReference<>() {
        });

    if (!list.isEmpty()) {
      event.setViews(list.get(0).getHits());
    }

    return EventMapper.toEventFullDTO(event);
  }

  @Override
  public List<EventShortDTO> findEventsByUser(Long userId, Integer from, Integer size) {
    userRepository.findById(userId)
        .orElseThrow(() -> new NotFoundException(USER_NAME, userId.toString()));

    return eventRepository.findAllByInitiatorId(userId, pageRequestOf(from, size)).stream()
        .map(it -> modelMapper.map(it, EventShortDTO.class))
        .collect(Collectors.toList());
  }

  @Override
  @SneakyThrows
  public EventFullDTO create(Long userId, NewEventDTO body) {
    User user = userRepository.findById(userId)
        .orElseThrow(() -> new NotFoundException(USER_NAME, userId.toString()));

    if (LocalDateTime.now().isAfter(body.getEventDate())) {
      throw new ValidationException();
    }
    if (checkEventDate(body.getEventDate())) {
      throw new ConflictException();
    }

    Category category = categoryRepository.findById(body.getCategory())
        .orElseThrow(() -> new NotFoundException(CATEGORY_NAME, body.getCategory().toString()));

    Event event = EventMapper.toEvent(body, category);

    event.setInitiator(user);
    event.setCreatedOn(LocalDateTime.now());
    event.setState(EventStatus.PENDING);

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
      EventStatus status =
          body.getStateAction().equals(UserEventRequestStatus.CANCEL_REVIEW) ? EventStatus.CANCELED
              : EventStatus.PENDING;
      event.setState(status);
    }

    Event result = eventRepository.saveAndFlush(event);

    return EventMapper.toEventFullDTO(result);
  }

  private boolean checkEventDate(LocalDateTime eventDate) {
    return Duration.between(LocalDateTime.now(), eventDate).toHours() < 2L;
  }

  private ResponseEntity<Object> getEventsViewStats(List<Event> events) {
    String[] uri = getStatsUrisByEvents(events);
    LocalDateTime start = getStartDateTimeForStatsView(events);
    return statsClient.getStats(start, LocalDateTime.now(), uri, true);
  }

  private LocalDateTime getStartDateTimeForStatsView(List<Event> events) {
    return events.stream()
        .map(Event::getCreatedOn)
        .sorted()
        .findFirst()
        .orElseThrow();
  }

  private String[] getStatsUrisByEvents(List<Event> events) {
    return events.stream()
        .map(it -> "/events/" + it.getId())
        .toArray(String[]::new);
  }

  private Map<Long, Long> getEventsHits(List<EventViewStats> list) {
    Map<Long, Long> hits = new HashMap<>();
    for (EventViewStats eventViewStats : list) {
      String[] temp = eventViewStats.getUri().split("/");
      Long eventId = Long.parseLong(temp[temp.length - 1]);
      hits.put(eventId, eventViewStats.getHits());
    }
    return hits;
  }

}
