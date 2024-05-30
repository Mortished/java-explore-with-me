package ru.practicum.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.practicum.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {

  @Query(value = "SELECT * FROM users u WHERE u.id IN ?1 LIMIT ?2 OFFSET ?3", nativeQuery = true)
  List<User> findByIdIsIn(List<Integer> ids, Integer size, Integer from);


  @Query(value = "SELECT * FROM users u LIMIT ?1 OFFSET ?2", nativeQuery = true)
  List<User> findAllWithPaging(Integer size, Integer from);

}
