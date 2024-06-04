package ru.practicum.controller.admin;

import java.util.List;
import javax.validation.Valid;
import javax.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import ru.practicum.model.dto.UserDTO;
import ru.practicum.service.UserService;

@RestController
@AllArgsConstructor
@RequestMapping("/admin/users")
@Validated
@Slf4j
public class UserAdminController {

  private final UserService userService;

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public UserDTO create(@Valid @RequestBody final UserDTO user) {
    log.info("POST /admin/users - with body: {}", user);
    return userService.save(user);
  }

  @DeleteMapping("/{userId}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void delete(@PathVariable("userId") Long userId) {
    log.info("DELETE /admin/users/{userId} - with userId: {}", userId);
    userService.delete(userId);
  }

  @GetMapping
  public List<UserDTO> getUsers(
      @RequestParam(required = false) final List<Integer> ids,
      @RequestParam(defaultValue = "0") @Min(0) final Integer from,
      @RequestParam(defaultValue = "10") @Min(1) final Integer size
  ) {
    log.info("GET /admin/users with params: [ids: {}, from: {}, size: {}]", ids, from, size);
    return userService.getUsers(ids, from, size);
  }

}
