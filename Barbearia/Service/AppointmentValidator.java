package br.com.atividade.barbearia.Service;

import br.com.atividade.barbearia.DTOs.Appointment.AppointmentRequestDTO;
import br.com.atividade.barbearia.Exception.InvalidTimeException;
import br.com.atividade.barbearia.Exception.TimeSlotUnavailableException;
import br.com.atividade.barbearia.Model.BarberService;
import br.com.atividade.barbearia.Repository.AppointmentRepository;
import br.com.atividade.barbearia.Repository.ServiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class AppointmentValidator {
    private final AppointmentRepository appointmentRepository;
    private final ServiceRepository serviceRepository;

    @Autowired
    public AppointmentValidator(AppointmentRepository appointmentRepository, ServiceRepository serviceRepository) {
        this.appointmentRepository = appointmentRepository;
        this.serviceRepository = serviceRepository;
    }

    public void validate(AppointmentRequestDTO requestDTO) {


        LocalDateTime appointmentDate = requestDTO.appointmentDate();

        if(appointmentDate.getHour() < 9 || appointmentDate.getHour() > 18){
            throw new InvalidTimeException("The Appointment date must be between 9 to 18");
        }

        if(appointmentDate.isBefore(LocalDateTime.now().plusMinutes(30))){
            throw new InvalidTimeException("Appointment date must respect 30 minutes of cooldown");
        }

        BarberService service = serviceRepository.findById(requestDTO.serviceId())
                .orElseThrow(()-> new RuntimeException("Service not found"));

       LocalDateTime startTime = appointmentDate;
        LocalDateTime endTime = appointmentDate.plusMinutes(service.getDurationMinutes());


        boolean hasConflict= appointmentRepository.findByAppointmentDateBetween(startTime, endTime)
                .stream()
                .anyMatch(appointment -> appointment.getStatus()
                        .isActive() && !appointment.getId()
                        .equals(requestDTO.serviceId())
                );

        if(hasConflict){
            throw new TimeSlotUnavailableException(
                    "Slot not available, conflicting with another appointment from"+
                            startTime.toLocalTime() + " to " + endTime.toLocalTime()
            );
        }
    }

}
