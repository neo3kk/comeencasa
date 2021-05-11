package com.rest.comeencasa.service;

import com.rest.comeencasa.entities.Usuario;
import com.rest.comeencasa.repos.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LoginService {
    @Autowired
    UsuarioRepository usuarioRepository;

    public boolean checkUserAndPassword(String username, String password) {
        try {
            Usuario user = usuarioRepository.findUsuarioByEmailAndPassword(username, password);
            if (user == null) {
                return false;
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

    }

}
