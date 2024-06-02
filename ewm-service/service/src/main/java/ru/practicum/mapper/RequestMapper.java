package ru.practicum.mapper;

import ru.practicum.entity.Request;
import ru.practicum.model.dto.ParticipationRequestDto;

public class RequestMapper {

  public static ParticipationRequestDto toRequestDTO(Request request) {
    return ParticipationRequestDto.builder()
        .created(request.getCreated())
        .event(request.getEvent().getId())
        .id(request.getId())
        .requester(request.getRequester().getId())
        .status(request.getStatus().name())
        .build();
  }

}
