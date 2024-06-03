package ru.practicum.model.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

@Data
public class UserDTO {

  Long id;

  @Email
  @NotEmpty
  @NotNull
  @Length(min = 6, max = 254)
  String email;

  @NotBlank
  @Length(min = 2, max = 250)
  String name;

}
