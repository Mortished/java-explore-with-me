package ru.practicum.controller;

import javax.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import ru.practicum.model.dto.CompilationDTO;
import ru.practicum.model.dto.NewCompilationRequestDTO;
import ru.practicum.model.dto.UpdateCompilationRequestDTO;
import ru.practicum.service.CompilationService;

@RestController
@AllArgsConstructor
@RequestMapping("/admin/compilations")
@Validated
@Slf4j
public class AdminCompilationController {

  private final CompilationService compilationService;

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public CompilationDTO save(@Valid @RequestBody final NewCompilationRequestDTO body) {
    log.info("POST /admin/compilations: {}", body);
    return compilationService.save(body);
  }

  @PatchMapping("/{compId}")
  public CompilationDTO update(@PathVariable Long compId,
      @Valid @RequestBody final UpdateCompilationRequestDTO body) {
    log.info("PATCH /admin/compilations/{} : body={}", compId, body);
    return compilationService.update(compId, body);
  }

  @DeleteMapping("/{compId}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void delete(@PathVariable Long compId) {
    log.info("DELETE /admin/compilations/{}", compId);
    compilationService.delete(compId);
  }

}
