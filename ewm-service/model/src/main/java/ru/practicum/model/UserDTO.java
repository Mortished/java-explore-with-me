package ru.practicum.model;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class UserDTO {

  Long id;

  @Email
  String email;

  @NotEmpty
  String name;

}
