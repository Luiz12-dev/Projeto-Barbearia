package br.com.atividade.barbearia.DTOs.Register;

import br.com.atividade.barbearia.Enum.Role;
import br.com.atividade.barbearia.Model.User;

import java.time.LocalDateTime;
import java.util.UUID;

public record RegisterResponseDTO(
        UUID id,
        String username,
        String email,
        Role role,
        String message,
        LocalDateTime createdAt

) {

    public static RegisterResponseDTO success(User user) {
        return new RegisterResponseDTO(
                user.getId(),
                user.getUsername(),
                user.getEmail(),
                user.getRole(),
                "Usuário cadastrado com sucesso",
                user.getCreatedAt()
        );

    }
}
