package ru.practicum.admin.service.impl;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.admin.repository.CompilationRepository;
import ru.practicum.admin.service.CompilationService;
import ru.practicum.exception.CompilationNotFoundException;
import ru.practicum.model.CompilationDTO;
import ru.practicum.model.CompilationRequestDTO;

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
        .orElseThrow(() -> new CompilationNotFoundException(compId.toString()));
    return null;
  }

  @Override
  public void delete(Long compId) {
    compilationRepository.findById(compId)
        .orElseThrow(() -> new CompilationNotFoundException(compId.toString()));
    compilationRepository.deleteById(compId);
  }

}
