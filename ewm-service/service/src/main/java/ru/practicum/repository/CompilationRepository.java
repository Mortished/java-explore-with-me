package ru.practicum.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.practicum.entity.Compilation;

public interface CompilationRepository extends JpaRepository<Compilation, Long> {

  @Query(value = "select * from compilations where pinned = ?1 limit ?3 offset ?2", nativeQuery = true)
  List<Compilation> findAllWithParams(Boolean pinned, Integer from, Integer size);

  @Query(value = "select * from compilations limit ?2 offset ?1", nativeQuery = true)
  List<Compilation> findAllWithPaging(Integer from, Integer size);

}
