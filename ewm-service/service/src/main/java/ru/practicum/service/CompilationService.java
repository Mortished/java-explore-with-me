package ru.practicum.service;

import ru.practicum.model.CompilationDTO;
import ru.practicum.model.CompilationRequestDTO;

public interface CompilationService {

  CompilationDTO save(CompilationRequestDTO body);

  CompilationDTO update(Long compId, CompilationRequestDTO body);

  void delete(Long compId);

}
