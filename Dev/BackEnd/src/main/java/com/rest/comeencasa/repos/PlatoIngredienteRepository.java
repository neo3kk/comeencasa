package com.rest.comeencasa.repos;

import com.rest.comeencasa.entities.Plato;
import com.rest.comeencasa.entities.PlatoIngrediente;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PlatoIngredienteRepository extends JpaRepository<PlatoIngrediente, Long> {
    List<PlatoIngrediente> findPlatoIngredientesByPlato(Plato plato);
}
