package ru.practicum.repository;

import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.practicum.entity.Event;

public interface EventRepository extends JpaRepository<Event, Long> {

  Page<Event> findAll(Specification<Event> specification, Pageable pageable);

  @Query(value = "SELECT * FROM events WHERE user_id = ?1 LIMIT ?3 OFFSET ?2", nativeQuery = true)
  List<Event> findAllByUserId(Long userId, Integer from, Integer size);

}
