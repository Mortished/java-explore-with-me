package ru.practicum.model.dto;

import javax.validation.constraints.NotBlank;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

@Data
public class CategoryDTO {

  private Long id;

  @NotBlank
  @Length(min = 1, max = 50)
  private String name;

}
