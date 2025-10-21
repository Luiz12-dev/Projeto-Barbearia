package br.com.atividade.barbearia.Controllers;

import br.com.atividade.barbearia.DTOs.Appointment.AppointmentRequestDTO;
import br.com.atividade.barbearia.DTOs.Appointment.AppointmentResponseDTO;
import br.com.atividade.barbearia.Enum.StateAppoint;
import br.com.atividade.barbearia.Model.User;
import br.com.atividade.barbearia.Service.AppointmentService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/appointments")
public class AppointmentController {

    private final AppointmentService appointmentService;

    @Autowired
    public AppointmentController(AppointmentService appointmentService) {
        this.appointmentService = appointmentService;
    }

    @PostMapping
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<AppointmentResponseDTO> create(@RequestBody @Valid AppointmentRequestDTO requestDTO, @AuthenticationPrincipal User user){
        AppointmentResponseDTO responseDTO = appointmentService.create(user.getUsername(),requestDTO);
        return new ResponseEntity<>(responseDTO, HttpStatus.CREATED);
    }

    @GetMapping
    @PreAuthorize("hasRole('OWNER')")
    public ResponseEntity <List<AppointmentResponseDTO>> findAll(){
        List<AppointmentResponseDTO> responseDTO = appointmentService.findAll();

        return new ResponseEntity<>(responseDTO, HttpStatus.OK);

    }

    @GetMapping("my-appointments")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<List<AppointmentResponseDTO>> findUserAppointments(@AuthenticationPrincipal User user){
        List<AppointmentResponseDTO> userAppointments = appointmentService.findUserAppointment(user.getUsername());

        return new ResponseEntity<>(userAppointments, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('USER', 'OWNER')")
    public ResponseEntity<AppointmentResponseDTO> findById(@PathVariable UUID id){
        AppointmentResponseDTO responseDTO = appointmentService.findById(id);

        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }

    @PatchMapping("{id}/cancel")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<AppointmentResponseDTO> cancelAppointment(@PathVariable UUID id, @AuthenticationPrincipal User user){
        AppointmentResponseDTO appointment = appointmentService.cancelAppointment(user.getUsername(), id);

        return new ResponseEntity<>(appointment, HttpStatus.OK);
    }
    @PatchMapping("/{id}/status")
    @PreAuthorize("hasRole('OWNER')")
    public ResponseEntity<AppointmentResponseDTO> updateStatus(@PathVariable UUID id, @RequestParam StateAppoint status){
        AppointmentResponseDTO updateAppointment = appointmentService.updateAppointment(id, status);

        return new ResponseEntity<>(updateAppointment, HttpStatus.OK);

    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('OWNER')")
    public ResponseEntity<Void> deleteAppointment(@PathVariable UUID id){
        appointmentService.delete(id);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
