package br.com.atividade.barbearia.Controllers;

import br.com.atividade.barbearia.DTOs.Service.ServiceRequestDTO;
import br.com.atividade.barbearia.DTOs.Service.ServiceResponseDTO;
import br.com.atividade.barbearia.Service.BarberServiceService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.PostMapping;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/services")
public class ServiceController {

    private final BarberServiceService barberServiceService;

    @Autowired
    public ServiceController(BarberServiceService barberServiceService){
        this.barberServiceService = barberServiceService;
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('OWNER', 'USER')")
    public ResponseEntity<ServiceResponseDTO> findServiceById(@PathVariable UUID id){
      ServiceResponseDTO serviceResponseDTO = barberServiceService.findById(id);

      return new ResponseEntity<>(serviceResponseDTO, HttpStatus.OK);
    }


    @GetMapping
    @PreAuthorize("hasAnyRole('USER', 'OWNER')")
    public ResponseEntity <List<ServiceResponseDTO>> findAllServices(){
        List<ServiceResponseDTO> services = barberServiceService.findALL();

        return new ResponseEntity<>(services, HttpStatus.OK);
    }


    @PostMapping
    @PreAuthorize("hasRole('OWNER')")
    public ResponseEntity<ServiceResponseDTO> createService(@Valid @RequestBody ServiceRequestDTO serviceRequestDTO){
        ServiceResponseDTO service = barberServiceService.create(serviceRequestDTO);

        return new ResponseEntity<>(service, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('OWNER')")
    public ResponseEntity<ServiceResponseDTO> updateService(@PathVariable UUID id, @Valid @RequestBody ServiceRequestDTO serviceRequestDTO){
        ServiceResponseDTO service = barberServiceService.update(id, serviceRequestDTO);
        return new ResponseEntity<>(service, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('OWNER')")
    public ResponseEntity<Void> deleteService(@PathVariable UUID id){
        barberServiceService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
