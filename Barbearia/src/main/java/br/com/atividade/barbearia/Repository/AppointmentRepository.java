package br.com.atividade.barbearia.Repository;

import br.com.atividade.barbearia.Enum.StateAppoint;
import br.com.atividade.barbearia.Model.Appointment;
import br.com.atividade.barbearia.Model.Service;
import br.com.atividade.barbearia.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public interface AppointmentRepository extends JpaRepository<Appointment, UUID> {
    List<Appointment> findByUserIdOrderByAppointmentDateDesc(UUID userId);


    List<Appointment> findByUser(User user);

    List<Appointment> findByService(Service service);

    List<Appointment> findByAppointmentDateBetween(LocalDateTime start, LocalDateTime end);



}
