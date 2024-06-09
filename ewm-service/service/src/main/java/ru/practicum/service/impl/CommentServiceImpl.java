package ru.practicum.service.impl;

import static ru.practicum.utils.Dictionary.COMMENT_NAME;
import static ru.practicum.utils.Dictionary.EVENT_NAME;
import static ru.practicum.utils.Dictionary.USER_NAME;

import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import ru.practicum.entity.Comment;
import ru.practicum.entity.Event;
import ru.practicum.entity.User;
import ru.practicum.exception.ConflictException;
import ru.practicum.exception.NotFoundException;
import ru.practicum.model.EventStatus;
import ru.practicum.model.dto.CommentDTO;
import ru.practicum.model.dto.NewCommentDTO;
import ru.practicum.repository.CommentRepository;
import ru.practicum.repository.EventRepository;
import ru.practicum.repository.UserRepository;
import ru.practicum.service.CommentService;

@Service
@AllArgsConstructor
public class CommentServiceImpl implements CommentService {

  private final CommentRepository commentRepository;
  private final EventRepository eventRepository;
  private final UserRepository userRepository;
  private final ModelMapper modelMapper;

  @Override
  public CommentDTO createComment(Long userId, Long eventId, NewCommentDTO body) {
    User user = userRepository.findById(userId)
        .orElseThrow(() -> new NotFoundException(USER_NAME, userId.toString()));

    Event event = eventRepository.findById(eventId)
        .orElseThrow(() -> new NotFoundException(EVENT_NAME, eventId.toString()));

    if (!event.getState().equals(EventStatus.PUBLISHED)) {
      throw new ConflictException();
    }

    Comment comment = Comment.builder()
        .author(user)
        .event(event)
        .text(body.getText())
        .build();

    return modelMapper.map(commentRepository.save(comment), CommentDTO.class);
  }

  @Override
  public CommentDTO updateComment(Long userId, Long eventId, Long commentId, NewCommentDTO body) {
    userRepository.findById(userId)
        .orElseThrow(() -> new NotFoundException(USER_NAME, userId.toString()));

    Event event = eventRepository.findById(eventId)
        .orElseThrow(() -> new NotFoundException(EVENT_NAME, eventId.toString()));

    Comment comment = commentRepository.findById(commentId)
        .orElseThrow(() -> new NotFoundException(COMMENT_NAME, eventId.toString()));

    if (!event.getState().equals(EventStatus.PUBLISHED)
        || !comment.getAuthor().getId().equals(userId)
    ) {
      throw new ConflictException();
    }

    comment.setText(body.getText());
    return modelMapper.map(commentRepository.saveAndFlush(comment), CommentDTO.class);
  }

}
