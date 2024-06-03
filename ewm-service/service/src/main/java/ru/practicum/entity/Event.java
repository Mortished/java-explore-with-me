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
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;
import ru.practicum.model.EventStatus;

@Data
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "events")
@DynamicInsert
public class Event {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false)
  private String annotation;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "category_id", nullable = false)
  private Category category;

  @ColumnDefault("0")
  @Column(nullable = false)
  private Integer confirmedRequests;

  private LocalDateTime createdOn;

  private String description;

  @Column(nullable = false)
  private LocalDateTime eventDate;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "user_id", nullable = false)
  private User initiator;

  private String location;

  @ColumnDefault("false")
  private boolean paid;

  @ColumnDefault("0")
  @Column(nullable = false)
  private Integer participantLimit;

  private LocalDateTime publishedOn;

  @ColumnDefault("true")
  private boolean requestModeration;

  @Enumerated(EnumType.STRING)
  private EventStatus state;

  @Column(nullable = false)
  private String title;

  private Long views;

}
