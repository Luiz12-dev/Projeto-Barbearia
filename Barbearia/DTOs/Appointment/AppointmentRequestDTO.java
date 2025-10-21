package br.com.atividade.barbearia.DTOs.Appointment;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;
import java.util.UUID;

public record AppointmentRequestDTO (
        @NotNull(message = "the service id is required")
        UUID serviceId,

        @NotNull(message = "the date is required")
        @Future(message = "the date must be in the future")
        LocalDateTime appointmentDate,

        String notes
){

}
