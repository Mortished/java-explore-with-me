package ru.practicum.stats.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.practicum.stats.entity.Hit;

@Repository
public interface HitsRepository extends JpaRepository<Hit, Long> {

}
