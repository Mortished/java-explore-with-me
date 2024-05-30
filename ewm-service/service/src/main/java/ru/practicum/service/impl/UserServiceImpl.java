package ru.practicum.service.impl;

import java.util.List;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import ru.practicum.entity.User;
import ru.practicum.exception.UserNotFoundException;
import ru.practicum.model.UserDTO;
import ru.practicum.repository.UserRepository;
import ru.practicum.service.UserService;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

  private final UserRepository userRepository;
  private final ModelMapper modelMapper = new ModelMapper();

  @Override
  public void save(UserDTO user) {
    userRepository.save(modelMapper.map(user, User.class));
  }

  @Override
  public void delete(Long userId) {
    userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException(userId.toString()));
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
