package ru.practicum.service;

import java.util.List;
import ru.practicum.model.dto.ParticipationRequestDto;

public interface RequestService {

  ParticipationRequestDto createRequest(Long userId, Long eventId);

  ParticipationRequestDto cancelRequest(Long userId, Long requestId);

  List<ParticipationRequestDto> findAllByUserId(Long userId);

}
