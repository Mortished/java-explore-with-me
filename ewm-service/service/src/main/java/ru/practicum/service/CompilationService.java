package ru.practicum.service;

import java.util.List;
import ru.practicum.model.dto.CompilationDTO;
import ru.practicum.model.dto.NewCompilationRequestDTO;
import ru.practicum.model.dto.UpdateCompilationRequestDTO;

public interface CompilationService {

  CompilationDTO save(NewCompilationRequestDTO body);

  CompilationDTO update(Long compId, UpdateCompilationRequestDTO body);

  void delete(Long compId);

  List<CompilationDTO> findAllWithParams(Boolean pinned, Integer from, Integer size);

  CompilationDTO findById(Long compId);

}
