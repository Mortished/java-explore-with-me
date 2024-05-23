package ru.practicum.stats.service.impl;

import java.time.LocalDateTime;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.model.HitDTO;
import ru.practicum.model.IStatsDTO;
import ru.practicum.stats.mapper.EntityMapper;
import ru.practicum.stats.repository.HitsRepository;
import ru.practicum.stats.service.StatsService;

@Service
@AllArgsConstructor
public class StatsServiceImpl implements StatsService {

  private final HitsRepository hitsRepository;

  @Override
  public void saveHit(HitDTO body) {
    hitsRepository.save(EntityMapper.toHit(body));
  }

  @Override
  public List<IStatsDTO> findHitsByParams(LocalDateTime start, LocalDateTime end, List<String> uris,
      Boolean unique) {
    if (uris != null && !uris.isEmpty()) {
      if (unique) {
        return hitsRepository.findDistinctHitsByUris(start, end, uris);
      }
      return hitsRepository.findHitsByUris(start, end, uris);
    }
    if (unique) {
      return hitsRepository.findDistinctHits(start, end);
    }
    return hitsRepository.findHits(start, end);
  }

}
