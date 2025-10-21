package br.com.atividade.barbearia.Model;

import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Getter
@Setter
@Builder
@Table(name = "services")
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class BarberService {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @NotBlank(message = "The name of the service cannot be empty")
    @Column(name = "serviceName",nullable = false, unique = true, length = 100)
    private String serviceName;

    @NotBlank(message = "the description cannot be empty")
    @Size(min = 3, max = 500, message = "The description cannot exceed 500 characters")
    @Column(name = "description",length =500)
    private String description;
    @Column(name="duration_minutes")
    private Integer durationMinutes;

    @NotNull(message = "The price must be declared")
    @DecimalMin(value = "0.01", message = "The price must be higher than zero")
    @Column(name = "price", nullable = false, precision = 10, scale = 2)
    private BigDecimal price;

    @Column(name = "active", nullable = false)
    private boolean active = true;

    @CreationTimestamp
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    public String getFormatedPrice(){
        return "R$ "+ price.toString();
    }

}
