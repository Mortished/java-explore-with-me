package ru.practicum.stats.controller;

import java.util.List;
import javax.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.practicum.model.HitDTO;
import ru.practicum.model.StatsDTO;
import ru.practicum.stats.entity.Hit;
import ru.practicum.stats.repository.HitsRepository;

@RestController
@AllArgsConstructor
@Validated
@Slf4j
public class StatsController {

  private final ModelMapper modelMapper = new ModelMapper();
  private final HitsRepository hitsRepository;

  /**
   * Сохранение информации о том, что на uri конкретного сервиса был отправлен запрос пользователем.
   * Название сервиса, uri и ip пользователя указаны в теле запроса.
   */
  @PostMapping("/hit")
  public ResponseEntity<HitDTO> saveHit(@Valid @RequestBody HitDTO hitDTO) {
    hitsRepository.save(modelMapper.map(hitDTO, Hit.class));
    log.info("POST /hit with body: {}", hitDTO);
    return ResponseEntity.status(HttpStatus.CREATED).build();
  }

  /**
   * Получение статистики по посещениям.
   *
   * @param start  Дата и время начала диапазона за который нужно выгрузить статистику
   * @param end    Дата и время конца диапазона за который нужно выгрузить статистику
   * @param uris   Список uri для которых нужно выгрузить статистику
   * @param unique Нужно ли учитывать только уникальные посещения (только с уникальным ip)
   */
  @GetMapping("/stats")
  public List<StatsDTO> getStats(
      @RequestParam String start,
      @RequestParam String end,
      @RequestParam(required = false) List<String> uris,
      @RequestParam(required = false) Boolean unique
  ) {
    log.info("GET /stats params: start={}, end={}, uri={}, unique={}", start, end, uris, unique);
    return List.of(new StatsDTO("test", "url", 6L));
  }


}
