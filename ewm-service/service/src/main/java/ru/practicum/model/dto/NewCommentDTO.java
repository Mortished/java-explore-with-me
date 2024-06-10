package ru.practicum.model.dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

@Data
public class NewCommentDTO {

  @NotNull
  @NotEmpty
  @Length(min = 5)
  private String text;

}
