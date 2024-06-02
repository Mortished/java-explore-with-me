package ru.practicum.service.impl;

import static ru.practicum.utils.Dictionary.EVENT_NAME;
import static ru.practicum.utils.Dictionary.REQUEST_NAME;
import static ru.practicum.utils.Dictionary.USER_NAME;

import java.util.List;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.entity.Event;
import ru.practicum.entity.Request;
import ru.practicum.entity.User;
import ru.practicum.exception.ConflictException;
import ru.practicum.exception.NotFoundException;
import ru.practicum.mapper.RequestMapper;
import ru.practicum.model.EventRequestStatus;
import ru.practicum.model.EventStatus;
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
    if (requestRepository.existsByRequesterIdAndEventId(userId, eventId)
        || !event.getState().equals(EventStatus.PUBLISHED)
        || (event.getParticipantLimit() > 0 && event.getConfirmedRequests()
        .equals(event.getParticipantLimit()))
    ) {
      throw new ConflictException();
    }
    Request request = Request.builder()
        .status(
            event.isRequestModeration() ? EventRequestStatus.PENDING : EventRequestStatus.CONFIRMED)
        .event(event)
        .requester(user)
        .build();

    Request savedRequest = requestRepository.save(request);

    if (!event.isRequestModeration()) {
      Integer count = event.getConfirmedRequests();
      event.setConfirmedRequests(count + 1);
      eventRepository.saveAndFlush(event);
    }

    return RequestMapper.toRequestDTO(savedRequest);
  }

  @Override
  public ParticipationRequestDto cancelRequest(Long userId, Long requestId) {
    User user = userRepository.findById(userId)
        .orElseThrow(() -> new NotFoundException(USER_NAME, userId.toString()));

    Request request = requestRepository.findById(requestId)
        .orElseThrow(() -> new NotFoundException(REQUEST_NAME, userId.toString()));

    Event event = eventRepository.findById(request.getEvent().getId())
        .orElseThrow(
            () -> new NotFoundException(EVENT_NAME, request.getEvent().getId().toString()));

    if (request.getStatus().equals(EventRequestStatus.CONFIRMED)) {
      Integer count = event.getConfirmedRequests();
      event.setConfirmedRequests(count - 1);
      eventRepository.saveAndFlush(event);
    }

    request.setStatus(EventRequestStatus.REJECTED);
    Request savedRequest = requestRepository.saveAndFlush(request);

    return RequestMapper.toRequestDTO(savedRequest);
  }

  @Override
  public List<ParticipationRequestDto> findAllByUserId(Long userId) {
    userRepository.findById(userId)
        .orElseThrow(() -> new NotFoundException(USER_NAME, userId.toString()));

    return requestRepository.findAllByRequesterId(userId).stream()
        .map(RequestMapper::toRequestDTO)
        .collect(Collectors.toList());
  }

}
