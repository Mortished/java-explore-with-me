package ru.practicum.entity;

import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.practicum.model.EventStatus;

@Data
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "events")
public class Event {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false)
  private String annotation;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "category_id", nullable = false)
  private Category category;

  private Integer confirmedRequests;

  private LocalDateTime createdOn;

  private String description;

  @Column(nullable = false)
  private LocalDateTime eventDate;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "user_id", nullable = false)
  private User initiator;

  private String location;

  @Column(name = "is_paid", nullable = false)
  private boolean paid;

  private Integer participantLimit;

  private LocalDateTime publishedOn;

  @Column(columnDefinition = "boolean default true")
  private boolean requestModeration;

  @Enumerated(EnumType.STRING)
  private EventStatus state;

  @Column(nullable = false)
  private String title;

  private Long views;

}
