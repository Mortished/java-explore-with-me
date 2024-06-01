package ru.practicum.repository;

import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.practicum.entity.Event;

public interface EventRepository extends JpaRepository<Event, Long> {

  @Query(value = "SELECT *"
      + "FROM events "
      + "WHERE id IN ?1 "
      + "AND state IN ?2 "
      + "AND category_id IN ?3 "
      + "AND event_date BETWEEN ?4 AND ?5 "
      + "LIMIT ?7 OFFSET ?6 ", nativeQuery = true)
  List<Event> findByAllParams(List<Long> users, List<String> states, List<Long> categories,
      LocalDateTime rangeStart, LocalDateTime rangeEnd, Integer from, Integer size);

  @Query(value = "SELECT * FROM events LIMIT ?2 OFFSET ?1", nativeQuery = true)
  List<Event> findAllWithPaging(Integer from, Integer size);
}
