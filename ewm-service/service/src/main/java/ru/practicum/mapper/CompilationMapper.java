package ru.practicum.mapper;

import java.util.Collections;
import java.util.List;
import ru.practicum.entity.Compilation;
import ru.practicum.entity.Event;
import ru.practicum.model.dto.CompilationRequestDTO;

public class CompilationMapper {

  public static Compilation toCompilation(CompilationRequestDTO dto, List<Event> eventList) {
    return Compilation.builder()
        .events(eventList != null ? eventList : Collections.emptyList())
        .pinned(dto.getPinned() != null ? dto.getPinned() : false)
        .title(dto.getTitle())
        .build();
  }

}
