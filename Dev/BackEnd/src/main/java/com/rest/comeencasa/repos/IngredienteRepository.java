package com.rest.comeencasa.repos;


import com.rest.comeencasa.entities.Ingrediente;
import org.springframework.data.jpa.repository.JpaRepository;


public interface IngredienteRepository extends JpaRepository<Ingrediente, Long> {

    Ingrediente findIngredienteByName(String name);
    Ingrediente findIngredienteById(Long id);
}

