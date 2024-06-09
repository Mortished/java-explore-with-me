package ru.practicum.model.dto;

import java.util.List;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

@Data
public class UpdateCompilationRequestDTO {

  private List<Long> events;
  private Boolean pinned;

  @Length(min = 1, max = 50)
  private String title;

}
