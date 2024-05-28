package ru.practicum.admin.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.admin.entity.Event;

public interface EventRepository extends JpaRepository<Event, Long> {

}
