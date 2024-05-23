package ru.practicum.stats.service;

import java.time.LocalDateTime;
import java.util.List;
import ru.practicum.model.HitDTO;
import ru.practicum.model.IStatsDTO;

public interface StatsService {

  void saveHit(HitDTO body);

  List<IStatsDTO> findHitsByParams(LocalDateTime start, LocalDateTime end, List<String> uris,
      Boolean unique);

}
