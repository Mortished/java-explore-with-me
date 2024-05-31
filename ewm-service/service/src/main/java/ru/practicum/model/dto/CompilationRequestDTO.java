package ru.practicum.model.dto;

import java.util.List;
import javax.validation.constraints.NotEmpty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

@Data
public class CompilationRequestDTO {

  private List<Long> events;
  private Boolean pinned;

  @NotEmpty
  @Length(min = 1, max = 50)
  private String title;

}
