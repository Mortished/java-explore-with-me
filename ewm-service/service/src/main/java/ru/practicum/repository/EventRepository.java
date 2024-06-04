package ru.practicum.repository;

import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.practicum.entity.Event;

public interface EventRepository extends JpaRepository<Event, Long> {

  @Query(value = "SELECT * "
      + "FROM events "
      + "WHERE (id IN ?1 OR ?1 IS null) "
      + "AND (state IN ?2 OR ?2 IS null) "
      + "AND (category_id IN ?3 OR ?3 IS null) "
      + "AND (event_date > ?4 OR ?4 IS null) "
      + "AND (event_date < ?5 OR ?5 IS null) "
      + "LIMIT ?7 OFFSET ?6 ", nativeQuery = true)
  List<Event> findByAllParams(List<Long> users, List<String> states, List<Long> categories,
      LocalDateTime rangeStart, LocalDateTime rangeEnd, Integer from, Integer size);

  @Query(value = "SELECT * FROM events LIMIT ?2 OFFSET ?1", nativeQuery = true)
  List<Event> findAllWithPaging(Integer from, Integer size);

  @Query(value = "SELECT * FROM events WHERE user_id = ?1 LIMIT ?3 OFFSET ?2", nativeQuery = true)
  List<Event> findAllByUserId(Long userId, Integer from, Integer size);

}
