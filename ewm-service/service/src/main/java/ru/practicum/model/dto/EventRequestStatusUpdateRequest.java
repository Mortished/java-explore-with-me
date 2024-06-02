package ru.practicum.model.dto;

import java.util.List;
import lombok.Data;
import ru.practicum.model.EventRequestUpdateStatus;

@Data
public class EventRequestStatusUpdateRequest {

  private List<Long> requestIds;
  private EventRequestUpdateStatus status;

}
