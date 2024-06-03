package ru.practicum.model.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDateTime;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Positive;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

@Data
public class NewEventDTO {

  @NotEmpty
  @NotBlank
  @Length(min = 20, max = 2000)
  private String annotation;

  @Positive
  private Long category;

  @NotEmpty
  @NotBlank
  @Length(min = 20, max = 7000)
  private String description;

  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
  private LocalDateTime eventDate;

  private LocationDTO location;

  private Boolean paid;

  private Integer participantLimit;

  private Boolean requestModeration;

  @NotEmpty
  @NotBlank
  @Length(min = 3, max = 120)
  private String title;

}
