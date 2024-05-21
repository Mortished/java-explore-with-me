package ru.practicum.stats.controller;

import javax.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.practicum.model.HitDTO;
import ru.practicum.stats.entity.Hit;
import ru.practicum.stats.repository.HitsRepository;

@RestController
@AllArgsConstructor
@Validated
@Slf4j
public class StatsController {

  private final ModelMapper modelMapper = new ModelMapper();
  private final HitsRepository hitsRepository;

  @PostMapping("/hit")
  public ResponseEntity<HitDTO> saveHit(@Valid @RequestBody HitDTO hitDTO) {
    hitsRepository.save(modelMapper.map(hitDTO, Hit.class));
    log.info("POST /hit with body: {}", hitDTO);
    return ResponseEntity.status(HttpStatus.CREATED).build();
  }
}
