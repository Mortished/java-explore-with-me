package ru.practicum.admin.repository;

import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.admin.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {

  List<User> findByIdIsIn(List<Integer> ids, Pageable pageable);

}
