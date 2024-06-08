package ru.practicum.repository;

import java.time.LocalDateTime;
import java.util.Collection;
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
  List<Event> findByAllParams(List<Long> users, List<String> states, Collection<Long> categories,
      LocalDateTime rangeStart, LocalDateTime rangeEnd, Integer from, Integer size);

  @Query(value = "SELECT * FROM events WHERE user_id = ?1 LIMIT ?3 OFFSET ?2", nativeQuery = true)
  List<Event> findAllByUserId(Long userId, Integer from, Integer size);

  @Query(value = "SELECT * "
      + "FROM events  "
      + "WHERE state = 'PUBLISHED' "
      + "  AND ((?1 IS NULL) OR "
      + "       ((annotation ILIKE concat('%', ?1, '%')) OR (description ILIKE concat('%', ?1, '%')))) "
      + "  AND (category_id IN (?2) OR ?2 is null) "
      + "  AND (paid = ?3 OR ?3 IS NULL) "
      + "  AND (event_date > ?4 OR ?4 IS null) "
      + "  AND (event_date < ?5 OR ?5 IS null) "
      + "  AND ((?6 = false) OR "
      + "       (?6 = true AND (participant_limit = 0 OR confirmed_requests < participant_limit))) "
      + "ORDER BY (CASE WHEN ?7 = 'EVENT_DATE' THEN event_date END), "
      + "         (CASE WHEN ?7 = 'VIEWS' THEN views END) "
      + "LIMIT ?9 OFFSET ?8 ", nativeQuery = true)
  List<Event> findByAllParams(String text, Collection<Long> categories, Boolean paid,
      LocalDateTime rangeStart, LocalDateTime rangeEnd, Boolean onlyAvailable, String sort,
      Integer from,
      Integer size);

}
