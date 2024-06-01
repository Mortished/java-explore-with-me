package ru.practicum.mapper;

import org.modelmapper.ModelMapper;
import ru.practicum.entity.Category;
import ru.practicum.entity.Event;
import ru.practicum.entity.Location;
import ru.practicum.model.AdminEventRequestStatus;
import ru.practicum.model.Status;
import ru.practicum.model.dto.UpdateEventAdminRequestDTO;

public class EventMapper {

  private static final ModelMapper modelMapper = new ModelMapper();

  public static Event toEvent(UpdateEventAdminRequestDTO dto, Category category) {
    return Event.builder()
        .annotation(dto.getAnnotation())
        .category(category)
        .description(dto.getDescription())
        .eventDate(dto.getEventDate())
        .location(modelMapper.map(dto.getLocation(), Location.class))
        .paid(dto.getPaid())
        .participantLimit(dto.getParticipantLimit())
        .requestModeration(dto.getRequestModeration())
        .state(dto.getStateAction().equals(AdminEventRequestStatus.PUBLISH_EVENT) ? Status.PUBLISHED
            : Status.CANCELED)
        .title(dto.getTitle())
        .build();
  }

}
