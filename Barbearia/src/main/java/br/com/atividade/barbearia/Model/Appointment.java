package br.com.atividade.barbearia.Model;

import br.com.atividade.barbearia.Enum.StateAppoint;
import jakarta.persistence.*;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false, updatable = false)
    private Long id;

    @NotNull(message = "The user must be declared")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @NotNull(message = "The service must be declared")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "service_id", nullable = false)
    private Service service;

    @NotNull(message = "The date must be declared")
    @Future(message = "The appointment date must be in the future")
    @Column(name = "date", nullable = false)
    private LocalDateTime appointmentDate;

    @NotNull(message = "The status must be declared")
    @Enumerated(EnumType.STRING)
    @Column(name = "stateDate", nullable = false)
    private StateAppoint stateAppoint;

    @Column(name = "notes", length = 500)
    private String notes;

    @CreationTimestamp
    @Column(name = "creatApp")
    private LocalDateTime creatTime;

    @UpdateTimestamp
    @Column(name = "updateApp")
    private LocalDateTime updateTime;

    @PrePersist
    public void setDefaltStatus(){
        if(this.stateAppoint == null){
            this.stateAppoint = StateAppoint.SCHEDULED;
        }
    }
}