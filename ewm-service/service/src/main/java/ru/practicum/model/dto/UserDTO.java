package ru.practicum.model.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

@Data
public class UserDTO {

  private Long id;

  @Email
  @NotBlank
  @Length(min = 6, max = 254)
  private String email;

  @NotBlank
  @Length(min = 2, max = 250)
  private String name;

}
