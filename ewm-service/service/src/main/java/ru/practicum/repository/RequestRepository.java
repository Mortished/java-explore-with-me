package ru.practicum.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.entity.Request;

public interface RequestRepository extends JpaRepository<Request, Long> {

  List<Request> findAllByRequester_Id(Long requester_id);

  boolean existsByRequester_IdAndEvent_Id(Long requester_id, Long event_id);

}
