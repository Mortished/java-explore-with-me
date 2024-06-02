package ru.practicum.mapper;

import java.util.List;
import ru.practicum.entity.Compilation;
import ru.practicum.entity.Event;
import ru.practicum.model.dto.CompilationRequestDTO;

public class CompilationMapper {

  public static Compilation toCompilation(CompilationRequestDTO dto, List<Event> eventList) {
    return Compilation.builder()
        .events(eventList)
        .pinned(dto.getPinned())
        .title(dto.getTitle())
        .build();
  }

}
