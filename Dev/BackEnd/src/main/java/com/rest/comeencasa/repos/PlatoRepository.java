package com.rest.comeencasa.repos;

import com.rest.comeencasa.entities.Plato;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PlatoRepository extends JpaRepository<Plato, Long> {
    List<Plato> findAll();

    Plato findAllById(Long id);

    Plato findPlatoByNombre(String nombre);
}
