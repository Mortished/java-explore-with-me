package ru.practicum.service.impl;

import static ru.practicum.utils.Dictionary.CATEGORY_NAME;
import static ru.practicum.utils.Dictionary.EVENT_NAME;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import ru.practicum.entity.Category;
import ru.practicum.entity.Event;
import ru.practicum.exception.EventUpdateConflictException;
import ru.practicum.exception.NotFoundException;
import ru.practicum.mapper.EventMapper;
import ru.practicum.model.Status;
import ru.practicum.model.dto.AdminEventRequestDTO;
import ru.practicum.model.dto.EventDTO;
import ru.practicum.repository.CategoryRepository;
import ru.practicum.repository.EventRepository;
import ru.practicum.service.EventService;

@Service
@AllArgsConstructor
public class EventServiceImpl implements EventService {

  private final EventRepository eventRepository;
  private final CategoryRepository categoryRepository;
  private final ModelMapper modelMapper;

  @Override
  public List<EventDTO> findByParams(List<Long> users, List<String> states, List<Long> categories,
      LocalDateTime rangeStart, LocalDateTime rangeEnd, Integer from, Integer size) {
    if (users != null && states != null && categories != null && rangeStart != null
        && rangeEnd != null) {
      return eventRepository.findByAllParams(users, states, categories, rangeStart, rangeEnd, from,
              size).stream()
          .map(it -> modelMapper.map(it, EventDTO.class))
          .collect(Collectors.toList());
    }
    //TODO Добавить остальные варианты выборки
    return eventRepository.findAllWithPaging(from, size).stream()
        .map(it -> modelMapper.map(it, EventDTO.class))
        .collect(Collectors.toList());
  }

  @Override
  public EventDTO update(Long eventId, AdminEventRequestDTO body) {
    Event event = eventRepository.findById(eventId)
        .orElseThrow(() -> new NotFoundException(EVENT_NAME, eventId.toString()));

    Category category = null;
    if (body.getCategory() != null) {
      category = categoryRepository.findById(body.getCategory())
          .orElseThrow(() -> new NotFoundException(CATEGORY_NAME, body.getCategory().toString()));
    }

    //TODO добавить проверку по времени от даты публикации и начала ивента
    if (!event.getState().equals(Status.PENDING)) {
      throw new EventUpdateConflictException();
    }

    return modelMapper.map(eventRepository.save(EventMapper.toEvent(body, category)),
        EventDTO.class);
  }

}
