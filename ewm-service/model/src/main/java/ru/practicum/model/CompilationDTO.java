package ru.practicum.model;

import java.util.List;
import javax.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CompilationDTO {

  private List<EventShortInfoDTO> events;

  @NotNull
  private Long id;

  @NotNull
  private boolean pinned;

  @NotNull
  private String title;

}
