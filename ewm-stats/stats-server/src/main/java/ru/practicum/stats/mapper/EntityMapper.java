package ru.practicum.stats.mapper;

import ru.practicum.model.HitDTO;
import ru.practicum.stats.entity.Hit;

public class EntityMapper {

  public static Hit toHit(HitDTO dto) {
    return Hit.builder()
        .app(dto.getApp())
        .ip(dto.getIp())
        .uri(dto.getUri())
        .timestamp(dto.getRequestDateTime())
        .build();
  }

}
