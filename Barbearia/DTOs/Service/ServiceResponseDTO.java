package br.com.atividade.barbearia.DTOs.Service;

import java.math.BigDecimal;
import java.util.UUID;

public record ServiceResponseDTO(
        UUID id,
        String serviceName,
        String description,
        BigDecimal price,
        Integer durationMinutes,
        boolean active
) {
}
