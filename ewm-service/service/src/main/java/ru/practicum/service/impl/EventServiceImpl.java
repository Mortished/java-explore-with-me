package ru.practicum.service.impl;

import static ru.practicum.utils.Dictionary.CATEGORY_NAME;
import static ru.practicum.utils.Dictionary.EVENT_NAME;
import static ru.practicum.utils.Dictionary.USER_NAME;

import com.fasterxml.jackson.core.JsonProcessingException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import ru.practicum.entity.Category;
import ru.practicum.entity.Event;
import ru.practicum.entity.User;
import ru.practicum.exception.EventUpdateConflictException;
import ru.practicum.exception.NotFoundException;
import ru.practicum.mapper.EventMapper;
import ru.practicum.model.EventSortType;
import ru.practicum.model.EventStatus;
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

  @Override
  public List<EventFullDTO> findByParams(List<Long> users, List<String> states,
      List<Long> categories,
      LocalDateTime rangeStart, LocalDateTime rangeEnd, Integer from, Integer size) {
    if (users != null && states != null && categories != null && rangeStart != null
        && rangeEnd != null) {
      return eventRepository.findByAllParams(users, states, categories, rangeStart, rangeEnd, from,
              size).stream()
          .map(it -> modelMapper.map(it, EventFullDTO.class))
          .collect(Collectors.toList());
    }
    //TODO Добавить остальные варианты выборки
    return eventRepository.findAllWithPaging(from, size).stream()
        .map(it -> modelMapper.map(it, EventFullDTO.class))
        .collect(Collectors.toList());
  }

  @Override
  public List<EventFullDTO> findByParams(String text, List<Long> categories, Boolean paid,
      LocalDateTime rangeStart, LocalDateTime rangeEnd, Boolean onlyAvailable, EventSortType sort,
      Integer from, Integer size) {
    //TODO Реализовать логику выборки и маппинг параметров
    return List.of();
  }

  @Override
  public EventFullDTO update(Long eventId, UpdateEventAdminRequestDTO body) {
    Event event = eventRepository.findById(eventId)
        .orElseThrow(() -> new NotFoundException(EVENT_NAME, eventId.toString()));

    Category category = null;
    if (body.getCategory() != null) {
      category = categoryRepository.findById(body.getCategory())
          .orElseThrow(() -> new NotFoundException(CATEGORY_NAME, body.getCategory().toString()));
    }

    //TODO добавить проверку по времени от даты публикации и начала ивента
    //Duration.between(tempDateTime, toDateTime).toHours()
    if (!event.getState().equals(EventStatus.PENDING)) {
      throw new EventUpdateConflictException();
    }
    try {
      eventRepository.save(EventMapper.toEvent(body, category));
    } catch (JsonProcessingException e) {
      log.warn(e.getMessage());
    }
    //TODO Сделать ответ
    return null;
  }

  @Override
  public EventFullDTO findById(Long eventId) {
    Event event = eventRepository.findById(eventId)
        .orElseThrow(() -> new NotFoundException(EVENT_NAME, eventId.toString()));
    //TODO Реализиовать логику выборки
    return null;
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
  public EventFullDTO create(Long userId, NewEventDTO body) {
    User user = userRepository.findById(userId)
        .orElseThrow(() -> new NotFoundException(USER_NAME, userId.toString()));

    if (checkEventDate(body.getEventDate())) {
      throw new EventUpdateConflictException();
    }
    Category category = categoryRepository.findById(body.getCategory())
        .orElseThrow(() -> new NotFoundException(CATEGORY_NAME, body.getCategory().toString()));

    Event event = null;
    try {
      event = EventMapper.toEvent(body, category);
    } catch (JsonProcessingException e) {
      log.warn(e.getMessage());
      throw new EventUpdateConflictException();
    }
    event.setInitiator(user);
    event.setCreatedOn(LocalDateTime.now());

    if (event.isRequestModeration()) {
      event.setState(EventStatus.PENDING);
    } else {
      event.setState(EventStatus.PUBLISHED);
      event.setPublishedOn(LocalDateTime.now());
    }

    Event result = eventRepository.save(event);

    return EventMapper.toEventFullDTO(result);
  }

  @Override
  public EventFullDTO findUserEventById(Long userId, Long eventId) {
    User user = userRepository.findById(userId)
        .orElseThrow(() -> new NotFoundException(USER_NAME, userId.toString()));
    Event event = eventRepository.findById(eventId)
        .orElseThrow(() -> new NotFoundException(EVENT_NAME, eventId.toString()));
    if (!event.getInitiator().equals(user)) {
      //TODO ошибка что переданный юзер != юзеру из ивента
    }
    return modelMapper.map(event, EventFullDTO.class);
  }

  @Override
  public EventFullDTO updateUserEvent(Long userId, Long eventId, UpdateEventUserRequestDTO body) {
    User user = userRepository.findById(userId)
        .orElseThrow(() -> new NotFoundException(USER_NAME, userId.toString()));
    Event event = eventRepository.findById(eventId)
        .orElseThrow(() -> new NotFoundException(EVENT_NAME, eventId.toString()));
    //TODO маппинг в Event и сохранение с обратным преобразованием в DTO
    return null;
  }

  private boolean checkEventDate(LocalDateTime eventDate) {
    return Duration.between(LocalDateTime.now(), eventDate).toHours() < 2L;
  }

}
