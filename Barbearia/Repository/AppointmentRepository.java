package br.com.atividade.barbearia.Repository;

import br.com.atividade.barbearia.Enum.StateAppoint;
import br.com.atividade.barbearia.Model.Appointment;
import br.com.atividade.barbearia.Model.BarberService;
import br.com.atividade.barbearia.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface AppointmentRepository extends JpaRepository<Appointment, UUID> {
    List<Appointment> findByUserIdOrderByAppointmentDateDesc(UUID userId);

    Optional<Appointment> findByAppointmentDate(LocalDateTime appointmentDate);

    List<Appointment> findByUser(User user);

    List<Appointment> findByStatus(StateAppoint status);

    List<Appointment> findByAppointmentDateBetween(LocalDateTime start, LocalDateTime end);

    @Override
    Optional<Appointment> findById(UUID uuid);


    List<Appointment> findByStatusIn(List<StateAppoint> stateUses);

    long countByStatusIn(List<StateAppoint> state);

    List<Appointment> findByAppointmentDateBetweenIn(LocalDateTime start, LocalDateTime end, List<StateAppoint> stateUses);



}
