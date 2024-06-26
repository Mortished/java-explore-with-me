package ru.practicum.service.impl;

import static ru.practicum.utils.CustomPageRequest.pageRequestOf;
import static ru.practicum.utils.Dictionary.COMPILATION_NAME;

import java.util.List;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import ru.practicum.entity.Compilation;
import ru.practicum.entity.Event;
import ru.practicum.exception.NotFoundException;
import ru.practicum.mapper.CompilationMapper;
import ru.practicum.model.dto.CompilationDTO;
import ru.practicum.model.dto.NewCompilationRequestDTO;
import ru.practicum.model.dto.UpdateCompilationRequestDTO;
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
  public CompilationDTO save(NewCompilationRequestDTO body) {
    List<Event> events = null;
    if (body.getEvents() != null && !body.getEvents().isEmpty()) {
      events = eventRepository.findAllById(body.getEvents());
    }

    Compilation result = compilationRepository.save(CompilationMapper.toCompilation(body, events));
    return modelMapper.map(result, CompilationDTO.class);
  }

  @Override
  public CompilationDTO update(Long compId, UpdateCompilationRequestDTO body) {
    Compilation compilation = compilationRepository.findById(compId)
        .orElseThrow(() -> new NotFoundException(COMPILATION_NAME, compId.toString()));

    if (body.getPinned() != null && body.getPinned() != compilation.getPinned()) {
      compilation.setPinned(body.getPinned());
    }
    if (body.getTitle() != null && !body.getTitle().equals(compilation.getTitle())) {
      compilation.setTitle(body.getTitle());
    }
    if (isEventsUpdateRequired(compilation, body)) {
      List<Event> events = eventRepository.findAllById(body.getEvents());
      compilation.setEvents(events);
    }

    Compilation saved = compilationRepository.saveAndFlush(compilation);
    return modelMapper.map(saved, CompilationDTO.class);
  }

  @Override
  public void delete(Long compId) {
    compilationRepository.findById(compId)
        .orElseThrow(() -> new NotFoundException(COMPILATION_NAME, compId.toString()));
    compilationRepository.deleteById(compId);
  }

  @Override
  public List<CompilationDTO> findAllWithParams(Boolean pinned, Integer from, Integer size) {
    if (pinned != null) {
      return compilationRepository.findAllByPinned(pinned, pageRequestOf(from, size)).stream()
          .map(it -> modelMapper.map(it, CompilationDTO.class))
          .collect(Collectors.toList());
    }
    return compilationRepository.findAll(pageRequestOf(from, size)).stream()
        .map(it -> modelMapper.map(it, CompilationDTO.class))
        .collect(Collectors.toList());
  }

  @Override
  public CompilationDTO findById(Long compId) {
    Compilation compilation = compilationRepository.findById(compId)
        .orElseThrow(() -> new NotFoundException(COMPILATION_NAME, compId.toString()));
    return modelMapper.map(compilation, CompilationDTO.class);
  }

  private boolean isEventsUpdateRequired(Compilation compilation,
      UpdateCompilationRequestDTO body) {
    return body.getEvents() != null && !body.getEvents().stream().sorted()
        .equals(compilation.getEvents().stream()
            .map(Event::getId)
            .sorted()
            .collect(Collectors.toList()));
  }

}
