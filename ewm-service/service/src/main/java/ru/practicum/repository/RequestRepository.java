package ru.practicum.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.entity.Request;

public interface RequestRepository extends JpaRepository<Request, Long> {

  List<Request> findAllByRequesterId(Long requesterId);

  boolean existsByRequesterIdAndEventId(Long requesterId, Long eventId);

  List<Request> findAllByEventId(Long eventId);

}
