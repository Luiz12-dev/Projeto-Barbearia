package br.com.atividade.barbearia.DTOs.User;

import br.com.atividade.barbearia.Enum.StateAppoint;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record UserRequestDTO(

        @NotBlank(message = "The username cannot be empty")
        @Size(min =2, max = 50, message = "max 50 characters")
        String username,

        @NotBlank(message = "The email cannot be empty")
        @Email
        String email,

        @NotBlank(message = "The password cannot be empty")
        @JsonIgnore
        @Size(min = 6, message = "the password must have at least 6 characters")
        String password,

        @NotNull(message = "The role must be declared")
        StateAppoint role
) {
}
