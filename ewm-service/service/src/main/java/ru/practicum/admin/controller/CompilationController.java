package ru.practicum.admin.controller;

import javax.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.practicum.admin.service.CompilationService;
import ru.practicum.model.CompilationDTO;
import ru.practicum.model.CompilationRequestDTO;

@RestController
@AllArgsConstructor
@RequestMapping("/admin/compilations")
@Validated
@Slf4j
public class CompilationController {

  private final CompilationService compilationService;

  @PostMapping
  public CompilationDTO save(@Valid @RequestBody final CompilationRequestDTO body) {
    return compilationService.save(body);
  }

  @PatchMapping("/{compId}")
  public CompilationDTO update(@PathVariable Long compId,
      @Valid @RequestBody final CompilationRequestDTO body) {
    return compilationService.update(compId, body);
  }

  @DeleteMapping("/{compId}")
  public void delete(@PathVariable Long compId) {
    compilationService.delete(compId);
  }

}
