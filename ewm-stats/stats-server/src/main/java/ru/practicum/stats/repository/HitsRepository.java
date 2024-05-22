package ru.practicum.stats.repository;

import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.practicum.model.IStatsDTO;
import ru.practicum.stats.entity.Hit;

@Repository
public interface HitsRepository extends JpaRepository<Hit, Long> {

  @Query(value = "select h.app, h.uri, count(h.*) as hits from hits h "
      + "where timestamp between ?1 and ?2 "
      + "group by h.uri, h.app "
      + "order by count(h.*) desc", nativeQuery = true)
  List<IStatsDTO> findHits(LocalDateTime start, LocalDateTime end);

  @Query(value = "select h.app, h.uri, count(distinct h.ip) as hits from hits h "
      + "where timestamp between ?1 and ?2 "
      + "group by h.uri, h.app "
      + "order by count(distinct h.ip) desc", nativeQuery = true)
  List<IStatsDTO> findDistinctHits(LocalDateTime start, LocalDateTime end);

  @Query(value = "select h.app, h.uri, count(distinct h.ip) as hits from hits h "
      + "where timestamp between ?1 and ?2 "
      + "and h.uri in (?3) "
      + "group by h.uri, h.app "
      + "order by count(distinct h.ip)", nativeQuery = true)
  List<IStatsDTO> findDistinctHitsByUris(LocalDateTime start, LocalDateTime end, List<String> uris);

  @Query(value = "select h.app, h.uri, count(h.*) as hits from hits h "
      + "where timestamp between ?1 and ?2 "
      + "and h.uri in (?3) "
      + "group by h.uri, h.app "
      + "order by count(h.*) desc", nativeQuery = true)
  List<IStatsDTO> findHitsByUris(LocalDateTime start, LocalDateTime end, List<String> uris);
}
