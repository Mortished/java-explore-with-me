package ru.practicum.admin.controller;

import java.util.List;
import java.util.stream.Collectors;
import javax.validation.Valid;
import javax.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.PageRequest;
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
import ru.practicum.admin.entity.User;
import ru.practicum.admin.repository.UserRepository;
import ru.practicum.model.UserDTO;

@RestController
@AllArgsConstructor
@RequestMapping("/admin/users")
@Validated
@Slf4j
public class UserController {

  private final UserRepository userRepository;
  private final ModelMapper modelMapper = new ModelMapper();

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public void create(@Valid @RequestBody final UserDTO user) {
    log.info("POST /admin/users - with body: {}", user);
    userRepository.save(modelMapper.map(user, User.class));
  }

  @DeleteMapping("/{userId}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void delete(@PathVariable("userId") Long userId) {
    log.info("DELETE /admin/users/{userId} - with userId: {}", userId);
    userRepository.deleteById(userId);
  }

  @GetMapping
  public List<UserDTO> getUsers(
      @RequestParam(required = false) final List<Integer> ids,
      @RequestParam(defaultValue = "0") @Min(0) final Integer from,
      @RequestParam(defaultValue = "10") @Min(1) final Integer size
  ) {
    log.info("GET /admin/users with params: [ids: {}, from: {}, size: {}]", ids, from, size);
    PageRequest.ofSize(size);
    return userRepository.findByIdIsIn(ids, PageRequest.of(from, size)).stream()
        .map(user -> modelMapper.map(user, UserDTO.class))
        .collect(Collectors.toList());
  }

}
