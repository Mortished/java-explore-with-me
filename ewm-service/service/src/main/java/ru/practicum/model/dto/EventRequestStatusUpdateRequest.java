package ru.practicum.model.dto;

import java.util.List;
import lombok.Data;
import ru.practicum.model.EventRequestStatus;

@Data
public class EventRequestStatusUpdateRequest {

  private List<Long> requestIds;
  private EventRequestStatus status;

}
