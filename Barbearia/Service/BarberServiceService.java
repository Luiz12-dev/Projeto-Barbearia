package br.com.atividade.barbearia.Service;

import br.com.atividade.barbearia.Model.BarberService;
import br.com.atividade.barbearia.DTOs.Service.ServiceRequestDTO;
import br.com.atividade.barbearia.DTOs.Service.ServiceResponseDTO;
import br.com.atividade.barbearia.Repository.ServiceRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class BarberServiceService {

    private final ServiceRepository serviceRepository;

    @Autowired
    public BarberServiceService(ServiceRepository serviceRepository) {
        this.serviceRepository = serviceRepository;
    }

    public List<ServiceResponseDTO> findALL(){
        return serviceRepository.findAll()
                .stream().map(this::toServiceResponseDTO)
                .collect(Collectors.toList());
    }

    public ServiceResponseDTO findById(UUID id){
        BarberService service = serviceRepository.findById(id)
                .orElseThrow(()-> new RuntimeException("Service not found"));

        return toServiceResponseDTO(service);
    }

    @Transactional
    public ServiceResponseDTO create(ServiceRequestDTO serviceRequestDTO){
        if(serviceRepository.findByServiceName(serviceRequestDTO.serviceName()).isPresent()){
            throw new RuntimeException("Service already exists");
        }

        BarberService barberService = new BarberService();
        barberService.setServiceName(serviceRequestDTO.serviceName());
        barberService.setDescription(serviceRequestDTO.description());
        barberService.setPrice(serviceRequestDTO.price());
        barberService.setDurationMinutes(serviceRequestDTO.durationMinutes());
        barberService.setActive(true);

        BarberService savedBarberService = serviceRepository.save(barberService);

        return toServiceResponseDTO(savedBarberService);
    }

    @Transactional
    public ServiceResponseDTO update(UUID id, ServiceRequestDTO serviceRequestDTO){
        BarberService service = serviceRepository.findById(id)
                .orElseThrow(()-> new RuntimeException("Service not found"));

        service.setServiceName(serviceRequestDTO.serviceName());
        service.setDescription(serviceRequestDTO.description());
        service.setPrice(serviceRequestDTO.price());
        service.setDurationMinutes(serviceRequestDTO.durationMinutes());

       BarberService savedService = serviceRepository.save(service);

       return toServiceResponseDTO(savedService);
    }

    @Transactional
    public void delete(UUID id){
        if(!serviceRepository.existsById(id)){
            throw new RuntimeException("Service not found");
        }
        serviceRepository.deleteById(id);
    }

    private ServiceResponseDTO toServiceResponseDTO(BarberService barberService){
        return new ServiceResponseDTO(
                barberService.getId(),
                barberService.getServiceName(),
                barberService.getDescription(),
                barberService.getPrice(),
                barberService.getDurationMinutes(),
                barberService.isActive()
        );
    }


}
