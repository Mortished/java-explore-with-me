package ru.practicum.service.impl;

import static ru.practicum.utils.Dictionary.COMPILATION_NAME;

import java.util.List;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import ru.practicum.entity.Compilation;
import ru.practicum.entity.Event;
import ru.practicum.exception.NotFoundException;
import ru.practicum.model.dto.CompilationDTO;
import ru.practicum.model.dto.CompilationRequestDTO;
import ru.practicum.repository.CompilationRepository;
import ru.practicum.repository.EventRepository;
import ru.practicum.service.CompilationService;

@Service
@AllArgsConstructor
public class CompilationServiceImpl implements CompilationService {

  private final CompilationRepository compilationRepository;
  private final EventRepository eventRepository;
  private final ModelMapper modelMapper;

  @Override
  public CompilationDTO save(CompilationRequestDTO body) {
    Compilation result = compilationRepository.save(modelMapper.map(body, Compilation.class));
    return modelMapper.map(result, CompilationDTO.class);
  }

  @Override
  public CompilationDTO update(Long compId, CompilationRequestDTO body) {
    Compilation compilation = compilationRepository.findById(compId)
        .orElseThrow(() -> new NotFoundException(COMPILATION_NAME, compId.toString()));

    if (body.getPinned() != null && body.getPinned() != compilation.isPinned()) {
      compilation.setPinned(body.getPinned());
    }
    if (body.getTitle() != null && !body.getTitle().equals(compilation.getTitle())) {
      compilation.setTitle(body.getTitle());
    }
    if (body.getEvents() != null && !body.getEvents().stream().sorted()
        .equals(compilation.getEvents().stream()
            .map(Event::getId)
            .sorted()
            .collect(Collectors.toList())
        )) {
      List<Event> events = eventRepository.findAllById(body.getEvents());
      compilation.setEvents(events);
    }

    Compilation saved = compilationRepository.save(compilation);
    return modelMapper.map(saved, CompilationDTO.class);
  }

  @Override
  public void delete(Long compId) {
    compilationRepository.findById(compId)
        .orElseThrow(() -> new NotFoundException(COMPILATION_NAME, compId.toString()));
    compilationRepository.deleteById(compId);
  }

}
