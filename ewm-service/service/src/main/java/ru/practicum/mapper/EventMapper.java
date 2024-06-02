package ru.practicum.mapper;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import ru.practicum.entity.Category;
import ru.practicum.entity.Event;
import ru.practicum.model.AdminEventRequestStatus;
import ru.practicum.model.EventStatus;
import ru.practicum.model.dto.CategoryDTO;
import ru.practicum.model.dto.EventFullDTO;
import ru.practicum.model.dto.LocationDTO;
import ru.practicum.model.dto.NewEventDTO;
import ru.practicum.model.dto.UpdateEventAdminRequestDTO;
import ru.practicum.model.dto.UserDTO;

@Component
public class EventMapper {

  private static final ObjectMapper objectMapper = new ObjectMapper();
  private static final ModelMapper modelMapper = new ModelMapper();

  public static Event toEvent(UpdateEventAdminRequestDTO dto, Category category)
      throws JsonProcessingException {
    return Event.builder()
        .annotation(dto.getAnnotation())
        .category(category)
        .description(dto.getDescription())
        .eventDate(dto.getEventDate())
        .location(objectMapper.writeValueAsString(dto.getLocation()))
        .paid(dto.getPaid())
        .participantLimit(dto.getParticipantLimit())
        .requestModeration(dto.getRequestModeration())
        .state(dto.getStateAction().equals(AdminEventRequestStatus.PUBLISH_EVENT)
            ? EventStatus.PUBLISHED
            : EventStatus.CANCELED)
        .title(dto.getTitle())
        .build();
  }

  public static Event toEvent(NewEventDTO dto, Category category) throws JsonProcessingException {
    return Event.builder()
        .annotation(dto.getAnnotation())
        .category(category)
        .description(dto.getDescription())
        .eventDate(dto.getEventDate())
        .location(objectMapper.writeValueAsString(dto.getLocation()))
        .paid(dto.getPaid())
        .participantLimit(dto.getParticipantLimit())
        .requestModeration(dto.getRequestModeration())
        .title(dto.getTitle())
        .build();
  }

  public static EventFullDTO toEventFullDTO(Event event) {
    return EventFullDTO.builder()
        .annotation(event.getAnnotation())
        .category(modelMapper.map(event.getCategory(), CategoryDTO.class))
        .confirmedRequests(event.getConfirmedRequests())
        .createdOn(event.getCreatedOn())
        .description(event.getDescription())
        .eventDate(event.getEventDate())
        .id(event.getId())
        .initiator(modelMapper.map(event.getInitiator(), UserDTO.class))
        .location(modelMapper.map(event.getLocation(),
            LocationDTO.class)) //возможно не будет работать objectMapper.readValue(event.getLocation(), LocationDTO.class)
        .paid(event.isPaid())
        .participantLimit(event.getParticipantLimit())
        .publishedOn(event.getPublishedOn())
        .requestModeration(event.isRequestModeration())
        .state(event.getState().name())
        .title(event.getTitle())
        .views(event.getViews()) //TODO Откуда берется счетчик?
        .build();
  }

}
