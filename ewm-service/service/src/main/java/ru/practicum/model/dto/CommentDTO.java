package ru.practicum.model.dto;

import lombok.Data;

@Data
public class CommentDTO {

  private Long id;
  private Long eventId;
  private Long authorId;
  private String text;

}
