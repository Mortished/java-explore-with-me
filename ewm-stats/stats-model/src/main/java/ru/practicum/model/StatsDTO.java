package ru.practicum.model;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class StatsDTO {

  @NotEmpty
  private String app;

  @NotEmpty
  private String uri;

  @PositiveOrZero
  private Long hits;

}
