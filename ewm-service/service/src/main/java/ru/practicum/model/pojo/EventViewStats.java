package ru.practicum.model.pojo;

import lombok.Data;

@Data
public class EventViewStats {

  private String app;
  private String uri;
  private Long hits;

}
