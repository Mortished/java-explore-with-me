package ru.practicum.model.dto;

import static ru.practicum.utils.Dictionary.DATE_TIME_PATTERN;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDateTime;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class EventFullDTO {

  @NotEmpty
  private String annotation;

  @NotNull
  private CategoryDTO category;

  private Integer confirmedRequests;

  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DATE_TIME_PATTERN)
  private LocalDateTime createdOn;

  private String description;

  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DATE_TIME_PATTERN)
  private LocalDateTime eventDate;

  private Long id;

  private UserDTO initiator;

  private LocationDTO location;

  private Boolean paid;

  private Integer participantLimit;

  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DATE_TIME_PATTERN)
  private LocalDateTime publishedOn;

  private Boolean requestModeration;

  private String state;

  @NotEmpty
  private String title;

  private Long views;

}
