package ru.practicum.service;

import java.util.List;
import ru.practicum.model.dto.CompilationDTO;
import ru.practicum.model.dto.CompilationRequestDTO;

public interface CompilationService {

  CompilationDTO save(CompilationRequestDTO body);

  CompilationDTO update(Long compId, CompilationRequestDTO body);

  void delete(Long compId);

  List<CompilationDTO> findAllWithParams(Boolean pinned, Integer from, Integer size);

  CompilationDTO findById(Long compId);

}
