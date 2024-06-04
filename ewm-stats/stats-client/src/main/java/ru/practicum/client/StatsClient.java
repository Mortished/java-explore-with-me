package ru.practicum.client;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.util.DefaultUriBuilderFactory;
import ru.practicum.model.HitDTO;

@Component
public class StatsClient extends BaseClient {

  private final String MAIN_SERVICE_NAME = "ewm-main-service";

  protected StatsClient(RestTemplateBuilder builder) {
    super(builder
        .uriTemplateHandler(new DefaultUriBuilderFactory("http://localhost:9090"))
        .requestFactory(HttpComponentsClientHttpRequestFactory::new)
        .build());
  }

  public ResponseEntity<Object> hit(String uri, String ip,
      LocalDateTime requestDateTime) {
    HitDTO body = new HitDTO(MAIN_SERVICE_NAME, uri, ip, requestDateTime);
    return post(body);
  }

  public ResponseEntity<Object> getStats(LocalDateTime startDateTime, LocalDateTime endDateTime) {
    Map<String, Object> parameters = Map.of("start", startDateTime, "end", endDateTime);
    return get("/stats?start={start}&end={end}", parameters);
  }

  public ResponseEntity<Object> getStats(LocalDateTime startDateTime, LocalDateTime endDateTime,
      List<String> uris) {
    Map<String, Object> parameters = Map.of(
        "start", startDateTime,
        "end", endDateTime,
        "uris", uris
    );
    return get("/stats?start={start}&end={end}&uris={uris}", parameters);
  }

  public ResponseEntity<Object> getStats(LocalDateTime startDateTime, LocalDateTime endDateTime,
      List<String> uris, boolean unique) {
    Map<String, Object> parameters = Map.of(
        "start", startDateTime,
        "end", endDateTime,
        "uris", uris,
        "unique", unique
    );
    return get("/stats?start={start}&end={end}&uris={uris}&unique={unique}", parameters);
  }

  public ResponseEntity<Object> getStats(LocalDateTime startDateTime, LocalDateTime endDateTime,
      boolean unique) {
    Map<String, Object> parameters = Map.of(
        "start", startDateTime,
        "end", endDateTime,
        "unique", unique
    );
    return get("/stats?start={start}&end={end}&unique={unique}", parameters);
  }

}
