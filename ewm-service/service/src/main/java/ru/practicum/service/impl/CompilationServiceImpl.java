package ru.practicum.service.impl;

import static ru.practicum.utils.Dictionary.COMPILATION_NAME;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.exception.NotFoundException;
import ru.practicum.model.dto.CompilationDTO;
import ru.practicum.model.dto.CompilationRequestDTO;
import ru.practicum.repository.CompilationRepository;
import ru.practicum.service.CompilationService;

@Service
@AllArgsConstructor
public class CompilationServiceImpl implements CompilationService {

  private final CompilationRepository compilationRepository;

  @Override
  public CompilationDTO save(CompilationRequestDTO body) {
    return null;
  }

  @Override
  public CompilationDTO update(Long compId, CompilationRequestDTO body) {
    compilationRepository.findById(compId)
        .orElseThrow(() -> new NotFoundException(COMPILATION_NAME, compId.toString()));
    return null;
  }

  @Override
  public void delete(Long compId) {
    compilationRepository.findById(compId)
        .orElseThrow(() -> new NotFoundException(COMPILATION_NAME, compId.toString()));
    compilationRepository.deleteById(compId);
  }

}
