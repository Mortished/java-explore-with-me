package ru.practicum.model.dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import lombok.Data;

@Data
public class NewCommentDTO {

  @NotNull
  @NotEmpty
  private String text;

}
