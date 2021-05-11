package com.rest.comeencasa.repos;


import com.rest.comeencasa.entities.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;


public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    Usuario findUsuarioByEmailAndPassword(String name, String pass);

    Usuario findUsuarioByName(String user);

    Usuario findUsuarioByEmail(String email);


}

