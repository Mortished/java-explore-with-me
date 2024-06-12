package ru.practicum.repository;

import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.entity.Event;

public interface EventRepository extends JpaRepository<Event, Long> {

  Page<Event> findAll(Specification<Event> specification, Pageable pageable);

  List<Event> findAllByInitiatorId(Long userId, Pageable pageable);

}
