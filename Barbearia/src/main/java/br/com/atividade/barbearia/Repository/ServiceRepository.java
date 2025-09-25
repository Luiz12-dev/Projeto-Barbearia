package br.com.atividade.barbearia.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ServiceRepository extends JpaRepository<Service, UUID> {
    Optional<Service> findByserviceName(String serviceName);

    List<Service> findByactiveTrue();

    List<Service> findByactiveFalse();

     boolean existsByserviceName(String serviceName);
}
