package ru.practicum.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.practicum.entity.Category;

public interface CategoryRepository extends JpaRepository<Category, Long> {

  @Query(value = "select * from categories limit ?2 offset ?1", nativeQuery = true)
  List<Category> findAllWithPaging(Integer from, Integer size);

}
