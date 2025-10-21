package br.com.atividade.barbearia.DTOs.Register;

import br.com.atividade.barbearia.Enum.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record RegisterDTO(
        @NotBlank(message = "The username cannot be empty")
        @Size(min = 3, max = 50, message = "Max characters 50")
        String username,

        @Email(message = "Incorrect email format")
        @NotBlank(message = "The email cannot be empty")
        String email,

        @NotBlank(message = "The password cannot be empety")
        String password,

        @NotNull(message = "the accounts Type must be declared")
        Role role

) {
}
