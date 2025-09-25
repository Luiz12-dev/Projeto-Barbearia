package br.com.atividade.barbearia.DTOs;

import br.com.atividade.barbearia.Enum.StateAppoint;

import java.time.LocalDateTime;
import java.util.UUID;

public record AppointmentResponseDTO(

        UUID id,
        UUID userId,
        String userName,
        UUID serviceId,
        String serviceName,
        LocalDateTime appointmentDate,
        StateAppoint status,
        String notes,
        LocalDateTime creatTime
) {
}
