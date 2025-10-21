package br.com.atividade.barbearia.Repository;

import br.com.atividade.barbearia.Model.BarberService;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface ServiceRepository extends JpaRepository<BarberService, UUID> {
    Optional<BarberService> findByServiceName(String serviceName);
}
