package com.rest.comeencasa.repos;


import com.rest.comeencasa.entities.Alergeno;
import com.rest.comeencasa.entities.AlergenosUsuario;
import com.rest.comeencasa.entities.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface AlergenosUsuarioRepository extends JpaRepository<AlergenosUsuario, Long> {

    List<AlergenosUsuario> findAlergenosUsuarioByUsuario(Usuario usuario);
    boolean findAlergenosUsuarioByAlergeno_Id(Long id);
    boolean findAllById(Long id);
}

