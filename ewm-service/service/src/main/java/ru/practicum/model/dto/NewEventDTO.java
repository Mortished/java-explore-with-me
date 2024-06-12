package ru.practicum.model.dto;

import static ru.practicum.utils.Dictionary.DATE_TIME_PATTERN;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDateTime;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

@Data
public class NewEventDTO {

  @NotBlank
  @Length(min = 20, max = 2000)
  private String annotation;

  @Positive
  private Long category;

  @NotBlank
  @Length(min = 20, max = 7000)
  private String description;

  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DATE_TIME_PATTERN)
  private LocalDateTime eventDate;

  private LocationDTO location;

  private Boolean paid;

  @PositiveOrZero
  private Integer participantLimit;

  private Boolean requestModeration;

  @NotBlank
  @Length(min = 3, max = 120)
  private String title;

}
