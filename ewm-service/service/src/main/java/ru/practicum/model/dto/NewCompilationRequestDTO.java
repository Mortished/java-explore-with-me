package ru.practicum.model.dto;

import java.util.List;
import javax.validation.constraints.NotBlank;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

@Data
public class NewCompilationRequestDTO {

  private List<Long> events;
  private Boolean pinned;

  @NotBlank
  @Length(min = 1, max = 50)
  private String title;

}
