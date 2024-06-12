package ru.practicum.stats.controller;

import java.time.LocalDateTime;
import java.util.List;
import javax.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.practicum.model.HitDTO;
import ru.practicum.model.IStatsDTO;
import ru.practicum.stats.service.StatsService;

@RestController
@AllArgsConstructor
@Validated
@Slf4j
public class StatsController {

  private static final String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
  private final StatsService statsService;

  /**
   * Сохранение информации о том, что на uri конкретного сервиса был отправлен запрос пользователем.
   * Название сервиса, uri и ip пользователя указаны в теле запроса.
   */
  @PostMapping("/hit")
  public ResponseEntity<HitDTO> saveHit(@Valid @RequestBody HitDTO hitDTO) {
    log.info("POST /hit with body: {}", hitDTO);
    statsService.saveHit(hitDTO);
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
  public List<IStatsDTO> getStats(
      @RequestParam @DateTimeFormat(pattern = DATE_FORMAT) LocalDateTime start,
      @RequestParam @DateTimeFormat(pattern = DATE_FORMAT) LocalDateTime end,
      @RequestParam(required = false) List<String> uris,
      @RequestParam(required = false, defaultValue = "false") Boolean unique
  ) {
    log.info("GET /stats params: start={}, end={}, uri={}, unique={}", start, end, uris, unique);
    return statsService.findHitsByParams(start, end, uris, unique);
  }


}
