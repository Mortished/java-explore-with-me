package ru.practicum.model.dto;

import java.util.List;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class EventRequestStatusUpdateResult {

  List<ParticipationRequestDto> confirmedRequests;
  List<ParticipationRequestDto> rejectedRequests;

}
