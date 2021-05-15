package com.rest.comeencasa.service;

import com.rest.comeencasa.entities.Usuario;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface UserService {


    List<Usuario> getAll();

    Usuario getUser(Usuario usuari);

    Usuario getUserByid(Long userId);

    boolean isRegistred(Usuario usuari);

    boolean validateUser(String user);

    boolean validatePass(String pass);

    boolean addUser(Usuario usuari);

    void deleteUser(Usuario userObject);

    boolean createUser(Usuario user);
}
