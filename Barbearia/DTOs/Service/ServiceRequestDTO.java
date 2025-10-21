package br.com.atividade.barbearia.DTOs.Service;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;

public record ServiceRequestDTO (
        @NotBlank(message = "The service name cannot be empty")
        String serviceName,

        @NotBlank(message = "The description cannot be empty")
        @Size(min= 3, max = 500,message = "Max characters is 500")
        String description,

        @NotNull(message = "The price cannot be null")
        @DecimalMin(value = "0.01", message = "The price must be above zero")
        BigDecimal price,

        @NotNull(message = "The duration time is necessary")
        Integer durationMinutes



){
}
