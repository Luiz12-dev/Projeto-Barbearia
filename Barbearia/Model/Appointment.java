package br.com.atividade.barbearia.Model;

import br.com.atividade.barbearia.Enum.StateAppoint;
import jakarta.persistence.*;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "appointments")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of= "id")
public class Appointment {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", unique = true, nullable = false, updatable = false)
    private UUID id;

    @NotNull(message = "The user must be declared")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @NotNull(message = "The service must be declared")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "service_id", nullable = false)
    private BarberService barberService;

    @NotNull(message = "The date must be declared")
    @Future(message = "The appointment date must be in the future")
    @Column(name = "date", nullable = false)
    private LocalDateTime appointmentDate;

    @NotNull(message = "The status must be declared")
    @Enumerated(EnumType.STRING)
    @Column(name = "stateDate", nullable = false)
    private StateAppoint status;

    @Column(name = "notes", length = 500)
    private String notes;

    @CreationTimestamp
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @PrePersist
    public void setDefaultStatus(){
        if(this.status == null){
            this.status = StateAppoint.SCHEDULED;
        }
    }
}