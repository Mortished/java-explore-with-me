package ru.practicum.repository;

import java.util.Collection;
import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {

  List<User> findAllByIdIn(Collection<Long> ids, Pageable pageable);

}
