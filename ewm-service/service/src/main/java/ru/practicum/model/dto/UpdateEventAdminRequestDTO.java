package ru.practicum.model.dto;

import static ru.practicum.utils.Dictionary.DATE_TIME_PATTERN;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDateTime;
import javax.validation.constraints.Positive;
import lombok.Data;
import org.hibernate.validator.constraints.Length;
import ru.practicum.model.AdminEventRequestStatus;

@Data
public class UpdateEventAdminRequestDTO {

  @Length(min = 20, max = 2000)
  private String annotation;

  @Positive
  private Long category;

  @Length(min = 20, max = 7000)
  private String description;

  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DATE_TIME_PATTERN)
  private LocalDateTime eventDate;

  private LocationDTO location;

  private Boolean paid;

  private Integer participantLimit;

  private Boolean requestModeration;

  private AdminEventRequestStatus stateAction;

  @Length(min = 3, max = 120)
  private String title;

}
