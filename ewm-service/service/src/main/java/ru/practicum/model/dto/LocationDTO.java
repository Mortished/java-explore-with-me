package ru.practicum.model.dto;

import javax.validation.constraints.Positive;
import lombok.Data;

@Data
public class LocationDTO {

  @Positive
  private Double lat;

  @Positive
  private Double lon;

}
