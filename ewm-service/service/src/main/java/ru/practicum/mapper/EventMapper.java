package ru.practicum.mapper;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Collections;
import java.util.stream.Collectors;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import ru.practicum.entity.Category;
import ru.practicum.entity.Event;
import ru.practicum.model.dto.CategoryDTO;
import ru.practicum.model.dto.CommentDTO;
import ru.practicum.model.dto.EventFullDTO;
import ru.practicum.model.dto.LocationDTO;
import ru.practicum.model.dto.NewEventDTO;
import ru.practicum.model.dto.UserDTO;

@Component
public class EventMapper {

  private static final ObjectMapper objectMapper = new ObjectMapper();
  private static final ModelMapper modelMapper = new ModelMapper();

  public static Event toEvent(NewEventDTO dto, Category category) throws JsonProcessingException {
    return Event.builder()
        .annotation(dto.getAnnotation())
        .category(category)
        .description(dto.getDescription())
        .eventDate(dto.getEventDate())
        .location(objectMapper.writeValueAsString(dto.getLocation()))
        .paid(dto.getPaid() != null ? dto.getPaid() : false)
        .participantLimit(dto.getParticipantLimit() != null ? dto.getParticipantLimit() : 0)
        .requestModeration(dto.getRequestModeration() != null ? dto.getRequestModeration() : true)
        .title(dto.getTitle())
        .views(0L)
        .confirmedRequests(0)
        .build();
  }

  public static EventFullDTO toEventFullDTO(Event event) throws JsonProcessingException {
    return EventFullDTO.builder()
        .annotation(event.getAnnotation())
        .category(modelMapper.map(event.getCategory(), CategoryDTO.class))
        .confirmedRequests(event.getConfirmedRequests())
        .createdOn(event.getCreatedOn())
        .description(event.getDescription())
        .eventDate(event.getEventDate())
        .id(event.getId())
        .initiator(modelMapper.map(event.getInitiator(), UserDTO.class))
        .location(modelMapper.map(objectMapper.readValue(event.getLocation(), LocationDTO.class),
            LocationDTO.class))
        .paid(event.getPaid())
        .participantLimit(event.getParticipantLimit())
        .publishedOn(event.getPublishedOn())
        .requestModeration(event.isRequestModeration())
        .state(event.getState().name())
        .title(event.getTitle())
        .views(event.getViews())
        .comments(
            event.getComments() == null ? Collections.emptyList() : event.getComments().stream()
                .map(it -> modelMapper.map(it, CommentDTO.class))
                .collect(Collectors.toList()))
        .build();
  }

}
