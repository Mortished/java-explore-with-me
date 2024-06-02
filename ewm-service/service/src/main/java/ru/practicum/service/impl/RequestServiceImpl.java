package ru.practicum.service.impl;

import static ru.practicum.utils.Dictionary.EVENT_NAME;
import static ru.practicum.utils.Dictionary.REQUEST_NAME;
import static ru.practicum.utils.Dictionary.USER_NAME;

import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.entity.Event;
import ru.practicum.entity.Request;
import ru.practicum.entity.User;
import ru.practicum.exception.NotFoundException;
import ru.practicum.model.dto.ParticipationRequestDto;
import ru.practicum.repository.EventRepository;
import ru.practicum.repository.RequestRepository;
import ru.practicum.repository.UserRepository;
import ru.practicum.service.RequestService;

@Service
@AllArgsConstructor
public class RequestServiceImpl implements RequestService {

  private final RequestRepository requestRepository;
  private final UserRepository userRepository;
  private final EventRepository eventRepository;

  @Override
  public ParticipationRequestDto createRequest(Long userId, Long eventId) {
    User user = userRepository.findById(userId)
        .orElseThrow(() -> new NotFoundException(USER_NAME, userId.toString()));
  Event event = eventRepository.findById(eventId)
      .orElseThrow(() -> new NotFoundException(EVENT_NAME, eventId.toString()));
  //TODO Добавить логику создания и обработка ошибок
    return null;
  }

  @Override
  public ParticipationRequestDto cancelRequest(Long userId, Long requestId) {
    User user = userRepository.findById(userId)
        .orElseThrow(() -> new NotFoundException(USER_NAME, userId.toString()));
    Request request = requestRepository.findById(requestId)
        .orElseThrow(() -> new NotFoundException(REQUEST_NAME, userId.toString()));
    //TODO Добавить логику отмены и обработка ошибок
    return null;
  }

  @Override
  public List<ParticipationRequestDto> findAllByUserId(Long userId) {
    User user = userRepository.findById(userId)
        .orElseThrow(() -> new NotFoundException(USER_NAME, userId.toString()));
    List<Request> result = requestRepository.findAllByRequester_Id(userId.intValue());
    //TODO добаить маппинг в DTO
    return List.of();
  }
}
