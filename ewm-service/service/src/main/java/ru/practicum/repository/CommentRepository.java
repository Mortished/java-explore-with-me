package ru.practicum.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.entity.Comment;

public interface CommentRepository extends JpaRepository<Comment, Long> {

}
