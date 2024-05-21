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

  protected StatsClient(RestTemplateBuilder builder) {
    super(builder
        .uriTemplateHandler(new DefaultUriBuilderFactory("http://localhost:9090"))
        .requestFactory(HttpComponentsClientHttpRequestFactory::new)
        .build());
  }

  /**
   * Сохранение информации о том, что к эндпоинту был запрос
   *
   * @param app             Идентификатор сервиса для которого записывается информация
   * @param uri             URI для которого был осуществлен запрос
   * @param ip              IP-адрес пользователя, осуществившего запрос
   * @param requestDateTime Дата и время, когда был совершен запрос к эндпоинту
   */
  public ResponseEntity<Object> hit(String app, String uri, String ip,
      LocalDateTime requestDateTime) {
    HitDTO body = new HitDTO(app, uri, ip, requestDateTime);
    return post(body);
  }

  /**
   * Получение статистики по посещениям.
   *
   * @param startDateTime Дата и время начала диапазона за который нужно выгрузить статистику
   * @param endDateTime   Дата и время конца диапазона за который нужно выгрузить статистику
   */
  public ResponseEntity<Object> getStats(LocalDateTime startDateTime, LocalDateTime endDateTime) {
    Map<String, Object> parameters = Map.of("start", startDateTime, "end", endDateTime);
    return get("/stats?start={start}&end={end}", parameters);
  }

  /**
   * Получение статистики по посещениям.
   *
   * @param startDateTime Дата и время начала диапазона за который нужно выгрузить статистику
   * @param endDateTime   Дата и время конца диапазона за который нужно выгрузить статистику
   * @param uris          Список uri для которых нужно выгрузить статистику
   */
  public ResponseEntity<Object> getStats(LocalDateTime startDateTime, LocalDateTime endDateTime,
      List<String> uris) {
    Map<String, Object> parameters = Map.of(
        "start", startDateTime,
        "end", endDateTime,
        "uris", uris
    );
    return get("/stats?start={start}&end={end}&uris={uris}", parameters);
  }

  /**
   * Получение статистики по посещениям.
   *
   * @param startDateTime Дата и время начала диапазона за который нужно выгрузить статистику
   * @param endDateTime   Дата и время конца диапазона за который нужно выгрузить статистику
   * @param uris          Список uri для которых нужно выгрузить статистику
   * @param unique        Нужно ли учитывать только уникальные посещения (только с уникальным ip)
   */
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

  /**
   * Получение статистики по посещениям.
   *
   * @param startDateTime Дата и время начала диапазона за который нужно выгрузить статистику
   * @param endDateTime   Дата и время конца диапазона за который нужно выгрузить статистику
   * @param unique        Нужно ли учитывать только уникальные посещения (только с уникальным ip)
   */
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
