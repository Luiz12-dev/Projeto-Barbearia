package br.com.atividade.barbearia.Service;

import br.com.atividade.barbearia.DTOs.Appointment.AppointmentRequestDTO;
import br.com.atividade.barbearia.DTOs.Appointment.AppointmentResponseDTO;
import br.com.atividade.barbearia.Enum.StateAppoint;
import br.com.atividade.barbearia.Model.Appointment;
import br.com.atividade.barbearia.Model.BarberService;
import br.com.atividade.barbearia.Model.User;
import br.com.atividade.barbearia.Repository.AppointmentRepository;
import br.com.atividade.barbearia.Repository.ServiceRepository;
import br.com.atividade.barbearia.Repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class AppointmentService {


    private final AppointmentRepository appointmentRepository;
    private final UserRepository userRepository;
    private final ServiceRepository serviceRepository;
    private final AppointmentValidator appointmentValidator;

    @Autowired
    public AppointmentService(AppointmentRepository appointmentRepository, UserRepository userRepository, ServiceRepository serviceRepository, AppointmentValidator appointmentValidator) {
        this.serviceRepository = serviceRepository;
        this.userRepository = userRepository;
        this.appointmentRepository = appointmentRepository;
        this.appointmentValidator = appointmentValidator;
    }

    @Transactional
    public AppointmentResponseDTO create(String username,AppointmentRequestDTO requestDTO){

        appointmentValidator.validate(requestDTO);


        User user = userRepository.findByUsername(username)
                .orElseThrow(()-> new RuntimeException("User not found"));


        BarberService service = serviceRepository.findById(requestDTO.serviceId())
                .orElseThrow(()-> new RuntimeException("Service not found"));



        Appointment newAppointment = new Appointment();
        newAppointment.setAppointmentDate(requestDTO.appointmentDate());
        newAppointment.setNotes(requestDTO.notes());
        newAppointment.setUser(user);
        newAppointment.setBarberService(service);
        newAppointment.setStatus(StateAppoint.SCHEDULED);

        Appointment savedAppointment = appointmentRepository.save(newAppointment);

        return toAppointmentResponseDTO(savedAppointment);

    }

    public List<AppointmentResponseDTO> findAll(){
        return appointmentRepository.findAll()
                .stream().map(this::toAppointmentResponseDTO).collect(Collectors.toList());
    }

    public AppointmentResponseDTO findById(UUID id){
       Appointment appointment = appointmentRepository.findById(id)
               .orElseThrow(()-> new RuntimeException("Appointment not found"));

        return toAppointmentResponseDTO(appointment);
    }


    public List<AppointmentResponseDTO> findUserAppointment(String username){
        User user =  userRepository.findByUsername(username)
                .orElseThrow(()-> new RuntimeException("User not found"));

        List<Appointment> appointments = appointmentRepository.findByUser(user);

        return appointments.stream()
                .map(this::toAppointmentResponseDTO)
                .collect(Collectors.toList());
    }

    @Transactional
    public AppointmentResponseDTO cancelAppointment(String name, UUID id){

        Appointment appointment = appointmentRepository.findById(id)
                .orElseThrow(()-> new RuntimeException("Appointment not found"));


        if(!appointment.getUser().getUsername().equals(name)){
            throw new RuntimeException("User not allowed to cancel appointment");
        }

        if(!appointment.getStatus().canBeCancelled()){
            throw new RuntimeException("Appointment cant be cancelled");
        }

        appointment.setStatus(StateAppoint.CANCELLED);
        Appointment cancelledAppointment = appointmentRepository.save(appointment);
        return toAppointmentResponseDTO(cancelledAppointment);
    }
    @Transactional
    public AppointmentResponseDTO updateAppointment(UUID id, StateAppoint stateAppoint){
        Appointment appointment = appointmentRepository.findById(id)
                .orElseThrow(()-> new RuntimeException("Appointment not found"));

            appointment.setStatus(stateAppoint);

            Appointment updatedAppointment = appointmentRepository.save(appointment);

            return toAppointmentResponseDTO(updatedAppointment);

    }

    @Transactional
    public void delete(UUID id){
        Appointment appointment = appointmentRepository.findById(id)
                .orElseThrow(()-> new RuntimeException("Appointment not found"));

        appointmentRepository.delete(appointment);
    }

    private AppointmentResponseDTO toAppointmentResponseDTO(Appointment appointment){
        return new AppointmentResponseDTO(
                appointment.getId(),
                appointment.getUser().getId(), // Adicionado userId
                appointment.getUser().getUsername(),
                appointment.getBarberService().getId(), // Adicionado serviceId
                appointment.getBarberService().getServiceName(),
                appointment.getAppointmentDate(),
                appointment.getStatus(),
                appointment.getNotes(),
                appointment.getCreatedAt()

        );
    }
}
