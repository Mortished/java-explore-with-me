package ru.practicum.service;

import ru.practicum.model.dto.CompilationDTO;
import ru.practicum.model.dto.CompilationRequestDTO;

public interface CompilationService {

  CompilationDTO save(CompilationRequestDTO body);

  CompilationDTO update(Long compId, CompilationRequestDTO body);

  void delete(Long compId);

}
