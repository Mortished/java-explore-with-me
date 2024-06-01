package ru.practicum.service.impl;

import static ru.practicum.utils.Dictionary.USER_NAME;

import java.util.List;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import ru.practicum.entity.User;
import ru.practicum.exception.NotFoundException;
import ru.practicum.model.dto.UserDTO;
import ru.practicum.repository.UserRepository;
import ru.practicum.service.UserService;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

  private final UserRepository userRepository;
  private final ModelMapper modelMapper;

  @Override
  public void save(UserDTO user) {
    userRepository.save(modelMapper.map(user, User.class));
  }

  @Override
  public void delete(Long userId) {
    userRepository.findById(userId)
        .orElseThrow(() -> new NotFoundException(USER_NAME, userId.toString()));
    userRepository.deleteById(userId);
  }

  @Override
  public List<UserDTO> getUsers(List<Integer> ids, Integer from, Integer size) {
    if (ids == null || ids.isEmpty()) {
      return userRepository.findAllWithPaging(size, from).stream()
          .map(u -> modelMapper.map(u, UserDTO.class))
          .collect(Collectors.toList());
    }
    return userRepository.findByIdIsIn(ids, size, from).stream()
        .map(user -> modelMapper.map(user, UserDTO.class))
        .collect(Collectors.toList());
  }

}
