package ru.practicum.service;

import java.util.List;
import ru.practicum.model.dto.UserDTO;

public interface UserService {

  UserDTO save(UserDTO user);

  void delete(Long userId);

  List<UserDTO> getUsers(List<Integer> ids, Integer from, Integer size);

}
