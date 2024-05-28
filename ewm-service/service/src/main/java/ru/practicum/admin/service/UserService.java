package ru.practicum.admin.service;

import java.util.List;
import ru.practicum.model.UserDTO;

public interface UserService {

  void save(UserDTO user);

  void delete(Long userId);

  List<UserDTO> getUsers(List<Integer> ids, Integer from, Integer size);

}
