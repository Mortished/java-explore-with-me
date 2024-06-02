package ru.practicum.model.dto;

import java.util.List;
import lombok.Data;

@Data
public class EventRequestStatusUpdateResult {

  List<ParticipationRequestDto> confirmedRequests;
  List<ParticipationRequestDto> rejectedRequests;

}
