package com.rest.comeencasa.repos;


import com.rest.comeencasa.entities.Alergeno;
import com.rest.comeencasa.entities.AlergenosUsuario;
import org.springframework.data.jpa.repository.JpaRepository;


public interface AlergenosUsuarioRepository extends JpaRepository<AlergenosUsuario, Long> {

    boolean findAlergenosUsuarioByAlergeno_Id(Long id);

    boolean findAllById(Long id);
}

