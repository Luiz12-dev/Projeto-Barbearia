package br.com.atividade.barbearia.DTOs.Login;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record LoginDTO(
        @Email(message = "It must be on email format")
        @NotBlank(message = "The email cannot be empty")
        String email,

        @NotBlank(message = "The password cannot be empty")
        String password

){
}
