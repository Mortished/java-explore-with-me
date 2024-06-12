package ru.practicum.model.dto;

import static ru.practicum.utils.Dictionary.DATE_TIME_PATTERN;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ParticipationRequestDto {

  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DATE_TIME_PATTERN)
  private LocalDateTime created;

  private Long event;

  private Long id;

  private Long requester;

  private String status;

}
