package ru.practicum.controller.pub;

import java.util.List;
import javax.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.practicum.model.dto.CompilationDTO;
import ru.practicum.service.CompilationService;

@RestController
@AllArgsConstructor
@RequestMapping("/compilations")
@Validated
@Slf4j
public class CompilationPublicController {

  private final CompilationService compilationService;

  @GetMapping
  public List<CompilationDTO> getCompilations(
      @RequestParam(required = false) Boolean pinned,
      @RequestParam(required = false, defaultValue = "0") @Min(0) Integer from,
      @RequestParam(required = false, defaultValue = "10") @Min(1) Integer size
  ) {
    log.info("GET /compilations: pinned={}, from={}, size={}", pinned, from, size);
    return compilationService.findAllWithParams(pinned, from, size);
  }

  @GetMapping("/{compId}")
  public CompilationDTO getCompilation(@PathVariable Long compId) {
    log.info("GET /compilations/{}", compId);
    return compilationService.findById(compId);
  }

}
