package ru.practicum.service;

import ru.practicum.model.dto.CommentDTO;
import ru.practicum.model.dto.NewCommentDTO;

public interface CommentService {

  CommentDTO createComment(Long userId, Long eventId, NewCommentDTO body);

  CommentDTO updateComment(Long userId, Long eventId, Long commentId, NewCommentDTO body);

  void deleteComment(Long eventId, Long commentId);

}
