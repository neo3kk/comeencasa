package com.rest.comeencasa.repos;


import com.rest.comeencasa.entities.Alergeno;
import com.rest.comeencasa.entities.Ingrediente;
import org.springframework.data.jpa.repository.JpaRepository;


public interface AlergenoRepository extends JpaRepository<Alergeno, Long> {

    Alergeno findAlergenoByName(String name);
}

