package ru.practicum.repository;

import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.practicum.entity.Event;

public interface EventRepository extends JpaRepository<Event, Long> {

  @Query(value = "SELECT * "
      + "FROM events "
      + "WHERE (user_id IN (?1) OR ?1 IS null) "
      + "AND (state IN (?2) OR ?2 IS null) "
      + "AND (category_id IN (?3) OR ?3 IS null) "
      + "AND (event_date > ?4 OR ?4 IS null) "
      + "AND (event_date < ?5 OR ?5 IS null) "
      + "LIMIT ?7 OFFSET ?6 ", nativeQuery = true)
  List<Event> findByAllParams(List<Long> users, List<String> states, List<Long> categories,
      LocalDateTime rangeStart, LocalDateTime rangeEnd, Integer from, Integer size);

  @Query(value = "SELECT * FROM events WHERE user_id = ?1 LIMIT ?3 OFFSET ?2", nativeQuery = true)
  List<Event> findAllByUserId(Long userId, Integer from, Integer size);

  @Query(value = "SELECT * "
      + "FROM events e "
      + "WHERE e.state = 'PUBLISHED' "
      + "  AND ((?1 IS NULL) OR "
      + "       ((e.annotation ILIKE concat('%', ?1, '%')) OR (e.description ILIKE concat('%', ?1, '%')))) "
      + "  AND (?2 IS NULL OR e.category_id IN (?2)) "
      + "  AND (?3 IS NULL OR e.paid = ?3) "
      + "  AND (?4 IS NULL OR event_date > ?4) "
      + "  AND (?5 IS NULL OR event_date < ?5) "
      + "  AND ((?6 = false) OR "
      + "       (?6 = true AND (participant_limit = 0 OR confirmed_requests < participant_limit))) "
      + "ORDER BY (CASE WHEN ?7 = 'EVENT_DATE' THEN event_date END), "
      + "         (CASE WHEN ?7 = 'VIEWS' THEN views END) "
      + "LIMIT ?9 OFFSET ?8 ", nativeQuery = true)
  List<Event> findByAllParams(String text, List<Long> categories, Boolean paid,
      LocalDateTime rangeStart, LocalDateTime rangeEnd, Boolean onlyAvailable, String sort,
      Integer from,
      Integer size);

}
