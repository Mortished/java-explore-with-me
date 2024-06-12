package ru.practicum.model.dto;

import java.util.List;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class EventRequestStatusUpdateResult {

  private List<ParticipationRequestDto> confirmedRequests;
  private List<ParticipationRequestDto> rejectedRequests;

}
