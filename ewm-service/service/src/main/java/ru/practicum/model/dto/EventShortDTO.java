package ru.practicum.model.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDateTime;
import lombok.Data;

@Data
public class EventShortDTO {

  private String annotation;

  private CategoryDTO category;

  private Integer confirmedRequests;

  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
  private LocalDateTime eventDate;

  private Long id;

  private UserDTO initiator;

  private Boolean paid;

  private String title;

  private Long views;

}