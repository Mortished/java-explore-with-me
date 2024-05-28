package ru.practicum.model;

import javax.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class CategoryDTO {

  Long id;

  @NotEmpty
  String name;

}
